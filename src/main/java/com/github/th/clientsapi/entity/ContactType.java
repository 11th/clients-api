package com.github.th.clientsapi.entity;

import java.util.Arrays;
import java.util.Optional;

public enum ContactType {
    PHONE("phone"),
    EMAIL("email");

    private final String value;

    ContactType(String value) {
        this.value = value;
    }

    public static Optional<ContactType> fromValue(String value) {
        return Arrays.stream(values())
                .filter(ct -> ct.value.equalsIgnoreCase(value))
                .findFirst();
    }
}
