package com.github.th.clientsapi.entity;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Client.class)
public class Client_ {
    public static volatile SingularAttribute<Client, Long> clientId;
    public static volatile SingularAttribute<Client, String> firstName;
    public static volatile SingularAttribute<Client, String> lastName;
    public static volatile ListAttribute<Client, Contact> phones;
    public static volatile ListAttribute<Client, Contact> emails;
}
