package com.github.th.clientsapi.mapper;

import com.github.th.clientsapi.dto.ContactDto;
import com.github.th.clientsapi.entity.*;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ContactMapper {
    public Contact toPhone(ContactDto contactDto, Client client) {
        Contact phone = new Phone();
        phone.setClient(client);
        phone.setValue(contactDto.getValue());
        return phone;
    }

    public Contact toEmail(ContactDto contactDto, Client client) {
        Contact email = new Email();
        email.setClient(client);
        email.setValue(contactDto.getValue());
        return email;
    }

    public ContactDto toContactDto(Contact contact) {
        ContactDto contactDto = new ContactDto();
        contactDto.setContactId(contact.getContactId());
        contactDto.setContactType(contact.getContactType());
        contactDto.setValue(contact.getValue());
        return contactDto;
    }
}
