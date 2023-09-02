package com.github.th.clientsapi.entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("PHONE")
public class Phone extends Contact {
    @Override
    public String getContactType() {
        return ContactType.PHONE.name();
    }
}
