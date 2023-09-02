package com.github.th.clientsapi.dto;

import com.github.th.clientsapi.entity.Contact;
import lombok.Data;

import java.util.List;

@Data
public class ClientWithContactsDto {
    private long clientId;
    private String firstName;
    private String lastName;
    private List<ContactDto> contacts;
}
