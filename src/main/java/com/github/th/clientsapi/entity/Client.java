package com.github.th.clientsapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long clientId;
    @Column(nullable = false)
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
    @Fetch(FetchMode.JOIN)
    private List<Phone> phones;
    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
    @Fetch(FetchMode.JOIN)
    private List<Email> emails = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return clientId == client.clientId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId);
    }
}
