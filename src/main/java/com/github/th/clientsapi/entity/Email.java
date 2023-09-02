package com.github.th.clientsapi.entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("EMAIL")
public class Email extends Contact {
    @Override
    public String getContactType() {
        return ContactType.EMAIL.name();
    }
}
