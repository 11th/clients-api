package com.github.th.clientsapi.controller;

import com.github.th.clientsapi.dto.ClientDto;
import com.github.th.clientsapi.dto.ClientWithContactsDto;
import com.github.th.clientsapi.dto.ContactDto;
import com.github.th.clientsapi.service.ClientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/clients")
@AllArgsConstructor
@Validated
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDto> addClient(@Valid @RequestBody ClientDto clientDto) {
        return ResponseEntity.ok(clientService.addClient(clientDto));
    }

    @PostMapping("/{id}/contacts")
    public ResponseEntity<ContactDto> addContact(@NotNull @PathVariable("id") Long id,
                                                 @Valid @RequestBody ContactDto contact) {
        return ResponseEntity.ok(clientService.addContact(id, contact));
    }

    @GetMapping
    public ResponseEntity<Page<ClientDto>> findClients(@RequestParam("offset") @Min(0) Integer offset,
                                                       @RequestParam("limit") @Min(1) @Max(50) Integer limit) {
        return ResponseEntity.ok(clientService.findClients(PageRequest.of(offset, limit)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientWithContactsDto> findClient(@NotNull @PathVariable("id") Long id) {
        return ResponseEntity.ok(clientService.findClient(id));
    }

    @GetMapping("/{id}/contacts")
    public ResponseEntity<Collection<ContactDto>> findClientContacts(@NotNull @PathVariable("id") Long id,
                                                                     @RequestParam(value = "type", required = false) String type) {
        return ResponseEntity.ok(clientService.findClientContacts(id, type));
    }
}
