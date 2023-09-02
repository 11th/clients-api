package com.github.th.clientsapi.controller;

import com.github.th.clientsapi.dto.ClientDto;
import com.github.th.clientsapi.dto.ClientWithContactsDto;
import com.github.th.clientsapi.dto.ContactDto;
import com.github.th.clientsapi.entity.ContactType;
import com.github.th.clientsapi.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/clients")
@AllArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDto> addClient(@RequestBody ClientDto clientDto) {
        return ResponseEntity.ok(clientService.addClient(clientDto));
    }

    @PostMapping("/{id}/contacts")
    public ResponseEntity<ContactDto> addContact(@PathVariable("id") Long id,
                                                 @RequestBody ContactDto contact) {
        return ResponseEntity.ok(clientService.addContact(id, contact));
    }

    @GetMapping
    public ResponseEntity<Collection<ClientDto>> findClients() {
        return ResponseEntity.ok(clientService.findClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientWithContactsDto> findClient(@PathVariable("id") Long id) {
        return ResponseEntity.ok(clientService.findClient(id));
    }

    @GetMapping("/{id}/contacts")
    public ResponseEntity<Collection<ContactDto>> findClientContacts(@PathVariable("id") Long id,
                                                                     @RequestParam(value = "type", required = false) String type) {
        return ResponseEntity.ok(clientService.findClientContacts(id, type));
    }
}
