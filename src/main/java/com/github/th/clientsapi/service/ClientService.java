package com.github.th.clientsapi.service;

import com.github.th.clientsapi.dto.ClientDto;
import com.github.th.clientsapi.dto.ClientWithContactsDto;
import com.github.th.clientsapi.dto.ContactDto;
import com.github.th.clientsapi.entity.*;
import com.github.th.clientsapi.exception.ClientNotFoundException;
import com.github.th.clientsapi.exception.ContactNotDeterminedException;
import com.github.th.clientsapi.exception.ContactNotValidException;
import com.github.th.clientsapi.exception.InvalidContactTypeException;
import com.github.th.clientsapi.filter.ClientFilterCriteria;
import com.github.th.clientsapi.filter.ClientSpecifications;
import com.github.th.clientsapi.mapper.ClientMapper;
import com.github.th.clientsapi.mapper.ContactMapper;
import com.github.th.clientsapi.repository.ClientRepository;
import com.github.th.clientsapi.repository.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.regex.Pattern;

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
                validatePhone(contactDto.getValue());
                return contactMapper.toPhone(contactDto, client);
            }
            case EMAIL -> {
                validateEmail(contactDto.getValue());
                return contactMapper.toEmail(contactDto, client);
            }
        }
        throw new ContactNotDeterminedException("Contact not determined");
    }

    public Page<ClientDto> findClients(Long clientId, String firstName, String lastName, String phone, String email,
                                       Pageable pageable) {
        ClientFilterCriteria filterCriteria = ClientFilterCriteria.builder()
                .clientId(clientId)
                .firstName(firstName)
                .lastName(lastName)
                .phone(phone)
                .email(email)
                .build();
        Specification<Client> specification = ClientSpecifications.createClientSpecifications(filterCriteria);
        return clientRepository.findAll(specification, pageable).map(clientMapper::toClientDto);
    }

    public ClientWithContactsDto findClient(long id) {
        Client client = getClientOrThrowException(id);
        return clientMapper.toClientWithContactsDto(client);
    }

    public Collection<ContactDto> findClientContacts(long id, String type) {
        Client client = getClientOrThrowException(id);
        if (type == null) {
            return clientMapper.toContactsList(client);
        }
        ContactType contactType = getContactTypeOrThrowException(type);
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

    private void validatePhone(String phone) {
        String regexPattern = "^((\\+7|7|8)+([0-9]){10})$";
        if (!Pattern.compile(regexPattern).matcher(phone).matches()) {
            throw new ContactNotValidException(String.format("Phone %s is not valid", phone));
        }
    }

    private void validateEmail(String email) {
        String regexPattern = "^(\\S+)@([a-z0-9-]+)(\\.)([a-z]{2,4})(\\.?)([a-z]{0,4})+$";
        if (!Pattern.compile(regexPattern).matcher(email).matches()) {
            throw new ContactNotValidException(String.format("Email %s is not valid", email));
        }
    }
}
