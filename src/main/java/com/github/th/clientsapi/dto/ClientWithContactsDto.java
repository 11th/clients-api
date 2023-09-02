package com.github.th.clientsapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClientWithContactsDto {
    private long clientId;
    private String firstName;
    private String lastName;
    private List<ContactDto> contacts;
}
