package com.github.th.clientsapi.filter;

import com.github.th.clientsapi.entity.Client;
import com.github.th.clientsapi.entity.Client_;
import com.github.th.clientsapi.entity.Contact;
import com.github.th.clientsapi.entity.Contact_;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ClientSpecifications {
    public static Specification<Client> createClientSpecifications(ClientFilterCriteria filterCriteria) {
        return clientIdEqualTo(filterCriteria.getClientId())
                .and(firstNameEqualTo(filterCriteria.getFirstName()))
                .and(lastNameEqualTo(filterCriteria.getLastName()))
                .and(phoneIn(filterCriteria.getPhone()))
                .and(emailIn(filterCriteria.getEmail()));
    }

    public static Specification<Client> clientIdEqualTo(Long clientId) {
        return (root, query, builder) -> {
            if (clientId == null) {
                return null;
            }
            return builder.equal(root.get(Client_.clientId), clientId);
        };
    }

    public static Specification<Client> firstNameEqualTo(String firstName) {
        return (root, query, builder) -> {
            if (firstName == null) {
                return null;
            }
            return builder.equal(root.get(Client_.firstName), firstName);
        };
    }

    public static Specification<Client> lastNameEqualTo(String lastName) {
        return (root, query, builder) -> {
            if (lastName == null) {
                return null;
            }
            return builder.equal(root.get(Client_.lastName), lastName);
        };
    }

    public static Specification<Client> phoneIn(String phone) {
        return (root, query, builder) -> {
            if (phone == null) {
                return null;
            }
            Join<Client, Contact> clientPhoneJoin = root.join(Client_.phones);
            return clientPhoneJoin.get(Contact_.value).in(List.of(phone));
        };
    }

    public static Specification<Client> emailIn(String email) {
        return (root, query, builder) -> {
            if (email == null) {
                return null;
            }
            Join<Client, Contact> clientEmailJoin = root.join(Client_.emails);
            return clientEmailJoin.get(Contact_.value).in(List.of(email));
        };
    }
}
