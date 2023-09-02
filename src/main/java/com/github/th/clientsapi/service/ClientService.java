package com.github.th.clientsapi.service;

import com.github.th.clientsapi.dto.ClientDto;
import com.github.th.clientsapi.dto.ClientWithContactsDto;
import com.github.th.clientsapi.dto.ContactDto;
import com.github.th.clientsapi.entity.*;
import com.github.th.clientsapi.exception.ClientNotFoundException;
import com.github.th.clientsapi.exception.ContactNotDeterminedException;
import com.github.th.clientsapi.exception.InvalidContactTypeException;
import com.github.th.clientsapi.mapper.ClientMapper;
import com.github.th.clientsapi.mapper.ContactMapper;
import com.github.th.clientsapi.repository.ClientRepository;
import com.github.th.clientsapi.repository.ContactRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ContactRepository contactRepository;
    private final ClientMapper clientMapper;
    private final ContactMapper contactMapper;

    @Transactional
    public ClientDto addClient(ClientDto clientDto) {
        Client client = clientRepository.save(clientMapper.toClient(clientDto));
        return clientMapper.toClientDto(client);
    }

    @Transactional
    public ContactDto addContact(long id, ContactDto contactDto) {
        Client client = getClientOrThrowException(id);
        Contact contact = determineNewContact(contactDto, client);
        contactRepository.save(contact);
        return contactMapper.toContactDto(contact);
    }

    private Contact determineNewContact(ContactDto contactDto, Client client) {
        ContactType contactType = getContactTypeOrThrowException(contactDto.getContactType());
        switch (contactType) {
            case PHONE -> {
                return contactMapper.toPhone(contactDto, client);
            }
            case EMAIL -> {
                return contactMapper.toEmail(contactDto, client);
            }
        }
        throw new ContactNotDeterminedException("Contact not determined");
    }

    //todo: добавить пагинацию
    public Collection<ClientDto> findClients() {
        List<ClientDto> clients = new ArrayList<>();
        clientRepository.findAll()
                .forEach(c -> clients.add(clientMapper.toClientDto(c)));
        return clients;
    }

    public ClientWithContactsDto findClient(long id) {
        Client client = getClientOrThrowException(id);
        return clientMapper.toClientWithContactsDto(client);
    }

    public Collection<ContactDto> findClientContacts(long id, String type) {
        Client client = getClientOrThrowException(id);
        ContactType contactType = getContactTypeOrThrowException(type);
        if (contactType == null) {
            return clientMapper.toContactsList(client);
        }
        switch (contactType) {
            case PHONE -> {
                return clientMapper.toPhonesList(client);
            }
            case EMAIL -> {
                return clientMapper.toEmailsList(client);
            }
            default -> {
                return clientMapper.toContactsList(client);
            }
        }
    }

    private Client getClientOrThrowException(long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(String.format("Client %s not found", id)));
    }

    private ContactType getContactTypeOrThrowException(String value) {
        return ContactType.fromValue(value)
                .orElseThrow(() -> new InvalidContactTypeException(String.format("Contact Type %s is invalid", value)));
    }
}
