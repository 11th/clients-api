package com.github.th.clientsapi.controller;

import com.github.th.clientsapi.dto.ClientDto;
import com.github.th.clientsapi.dto.ContactDto;
import com.github.th.clientsapi.entity.*;
import com.github.th.clientsapi.repository.ClientRepository;
import com.github.th.clientsapi.repository.ContactRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientControllerTest {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @LocalServerPort
    private Integer port;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ContactRepository contactRepository;


    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        contactRepository.deleteAll();
        clientRepository.deleteAll();
    }

    @Test
    void addClient_shouldReturnOk() {
        ClientDto client = new ClientDto();
        client.setFirstName("first");
        client.setLastName("last");
        given().contentType(ContentType.JSON).body(client)
                .when()
                .post("/api/clients")
                .then()
                .statusCode(200);
    }

    @Test
    void addClient_shouldReturnBadRequest() {
        ClientDto client = new ClientDto();
        client.setLastName("last");
        given().contentType(ContentType.JSON).body(client)
                .when()
                .post("/api/clients")
                .then()
                .statusCode(400);
    }

    @Test
    void addContact_shouldReturnOk() {
        addOneClient();
        ContactDto contact = new ContactDto();
        contact.setContactType("PHONE");
        contact.setValue("+79511111111");
        given().contentType(ContentType.JSON).body(contact)
                .when()
                .post("/api/clients/1/contacts")
                .then()
                .statusCode(200);
    }

    @Test
    void addContact_shouldReturnBadRequest() {
        addOneClient();
        ContactDto contact = new ContactDto();
        contact.setContactType("FAX");
        contact.setValue("+79511111111");
        given().contentType(ContentType.JSON).body(contact)
                .when()
                .post("/api/clients/1/contacts")
                .then()
                .statusCode(400);
    }

    @Test
    void findClients_shouldReturnOk() {
        addOneClient();
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/clients?offset=0&limit=10")
                .then()
                .statusCode(200);
    }

    @Test
    void findClient_shouldReturnOk() {
        addOneClient();
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/clients/1")
                .then()
                .statusCode(200);
    }

    @Test
    void findClient_shouldReturnNotFound() {
        addOneClient();
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/clients/2")
                .then()
                .statusCode(404);
    }

    @Test
    void findClientContacts_shouldReturnOk() {
        addOneClientWithContacts();
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/clients/1/contacts")
                .then()
                .statusCode(200);
    }

    @Test
    void findClientPhones_shouldReturnOk() {
        addOneClientWithContacts();
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/clients/1/contacts?type=phone")
                .then()
                .statusCode(200);
    }

    @Test
    void findClientEmails_shouldReturnOk() {
        addOneClientWithContacts();
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/clients/1/contacts?type=email")
                .then()
                .statusCode(200);
    }

    @Test
    void findClientContacts_shouldReturnBadRequest() {
        addOneClientWithContacts();
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/clients/1/contacts?type=fax")
                .then()
                .statusCode(400);
    }

    private void addOneClient() {
        Client client = new Client();
        client.setFirstName("first");
        client.setLastName("last");
        clientRepository.save(client);
    }

    private void addOneClientWithContacts() {
        Client client = new Client();
        client.setFirstName("first");
        client.setLastName("last");
        client = clientRepository.save(client);
        Phone phone = new Phone();
        phone.setClient(client);
        phone.setValue("+79511111111");
        contactRepository.save(phone);
        Email email = new Email();
        email.setClient(client);
        email.setValue("email@domen.ru");
        contactRepository.save(email);
    }
}