package com.github.th.clientsapi.repository;

import com.github.th.clientsapi.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
