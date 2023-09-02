package com.github.th.clientsapi.dto;

import lombok.Data;

@Data
public class ContactDto {
    private long contactId;
    private String contactType;
    private String value;
}
