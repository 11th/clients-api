package com.github.th.clientsapi.repository;

import com.github.th.clientsapi.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
