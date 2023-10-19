package com.github.th.clientsapi.controller;

import com.github.th.clientsapi.dto.ClientDto;
import com.github.th.clientsapi.dto.ClientWithContactsDto;
import com.github.th.clientsapi.dto.ContactDto;
import com.github.th.clientsapi.filter.ClientFilterCriteria;
import com.github.th.clientsapi.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/clients")
@AllArgsConstructor
@Validated
@Tag(name = "Clients")
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    @Operation(summary = "Add new client", responses = {
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(
                    implementation = ClientDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    public ResponseEntity<ClientDto> addClient(@Valid @RequestBody ClientDto clientDto) {
        return ResponseEntity.ok(clientService.addClient(clientDto));
    }

    @PostMapping("/{id}/contacts")
    @Operation(summary = "Add new contact for client", responses = {
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(
                    implementation = ContactDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    })
    public ResponseEntity<ContactDto> addContact(@NotNull @PathVariable("id") Long id,
                                                 @Valid @RequestBody ContactDto contact) {
        return ResponseEntity.ok(clientService.addContact(id, contact));
    }

    @GetMapping
    @Operation(summary = "Find all clients", responses = {
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(
                    implementation = Page.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    public ResponseEntity<Page<ClientDto>> findClients(@Valid ClientFilterCriteria filters, Pageable pageable) {
        return ResponseEntity.ok(clientService.findClients(filters, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find client by ID", responses = {
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(
                    implementation = ClientWithContactsDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    })
    public ResponseEntity<ClientWithContactsDto> findClient(@NotNull @PathVariable("id") Long id) {
        return ResponseEntity.ok(clientService.findClient(id));
    }

    @GetMapping("/{id}/contacts")
    @Operation(summary = "Find contacts of client", responses = {
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(
                    implementation = ContactDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    })
    public ResponseEntity<Collection<ContactDto>> findClientContacts(@NotNull @PathVariable("id") Long id,
                                                                     @RequestParam(value = "type", required = false) String type) {
        return ResponseEntity.ok(clientService.findClientContacts(id, type));
    }
}
