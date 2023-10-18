package com.github.th.clientsapi.filter;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ClientFilterCriteria {
    private Long clientId;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
}
