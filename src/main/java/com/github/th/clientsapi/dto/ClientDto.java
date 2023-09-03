package com.github.th.clientsapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClientDto {
    private long clientId;
    @NotBlank
    private String firstName;
    private String lastName;
}
