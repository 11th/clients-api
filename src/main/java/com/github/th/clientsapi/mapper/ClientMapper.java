package com.github.th.clientsapi.mapper;

import com.github.th.clientsapi.dto.ClientDto;
import com.github.th.clientsapi.dto.ClientWithContactsDto;
import com.github.th.clientsapi.dto.ContactDto;
import com.github.th.clientsapi.entity.Client;
import com.github.th.clientsapi.entity.Contact;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ClientMapper {
    private final ContactMapper contactMapper;

    public Client toClient(ClientDto clientDto) {
        Client client = new Client();
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        return client;
    }

    public ClientDto toClientDto(Client client) {
        ClientDto clientDto = new ClientDto();
        clientDto.setClientId(client.getClientId());
        clientDto.setFirstName(client.getFirstName());
        clientDto.setLastName(client.getLastName());
        return clientDto;
    }

    public ClientWithContactsDto toClientWithContactsDto(Client client) {
        ClientWithContactsDto clientDto = new ClientWithContactsDto();
        clientDto.setClientId(client.getClientId());
        clientDto.setFirstName(client.getFirstName());
        clientDto.setLastName(client.getLastName());
        clientDto.setContacts(toContactsList(client));
        return clientDto;
    }

    public List<ContactDto> toContactsList(Client client) {
        return getContactDtoList(getClientContacts(client));
    }

    public List<ContactDto> toPhonesList(Client client) {
        return getContactDtoList(getClientPhones(client));
    }

    public List<ContactDto> toEmailsList(Client client) {
        return getContactDtoList(getClientEmails(client));
    }

    private List<ContactDto> getContactDtoList(Collection<Contact> contacts) {
        return contacts.stream()
                .map(contactMapper::toContactDto)
                .collect(Collectors.toList());
    }

    private List<Contact> getClientContacts(Client client) {
        List<Contact> contacts = new ArrayList<>(client.getPhones());
        contacts.addAll(client.getEmails());
        return contacts;
    }

    private List<Contact> getClientPhones(Client client) {
        return new ArrayList<>(client.getPhones());
    }

    private List<Contact> getClientEmails(Client client) {
        return new ArrayList<>(client.getEmails());
    }
}
