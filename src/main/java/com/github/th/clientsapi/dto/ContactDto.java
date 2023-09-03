package com.github.th.clientsapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactDto {
    private long contactId;
    @NotBlank
    private String contactType;
    @NotBlank
    private String value;
}
