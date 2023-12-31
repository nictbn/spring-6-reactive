package com.example.spring6reactive.controllers;

import com.example.spring6reactive.model.CustomerDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.example.spring6reactive.controllers.CustomerController.CUSTOMER_PATH;
import static com.example.spring6reactive.controllers.CustomerController.CUSTOMER_PATH_ID;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class CustomerControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(1)
    void testListCustomers() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get().uri(CUSTOMER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(3);
    }

    @Test
    @Order(2)
    void testGetById() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get().uri(CUSTOMER_PATH_ID, 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody(CustomerDto.class);
    }

    @Test
    @Order(3)
    void testGetByIdNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get().uri(CUSTOMER_PATH_ID, 999)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(4)
    void testCreateCustomer() {

        webTestClient
                .mutateWith(mockOAuth2Login())
                .post().uri(CUSTOMER_PATH)
                .body(Mono.just(getCustomerDto()), CustomerDto.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/v2/customer/4");
    }

    @Test
    @Order(5)
    void testCreateCustomerBadData() {
        CustomerDto customerDto = getCustomerDto();
        customerDto.setCustomerName("");
        webTestClient
                .mutateWith(mockOAuth2Login())
                .post().uri(CUSTOMER_PATH)
                .body(Mono.just(customerDto), CustomerDto.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(6)
    void testUpdateCustomer() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .put()
                .uri(CUSTOMER_PATH_ID, 1)
                .body(Mono.just(getCustomerDto()), CustomerDto.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(7)
    void testUpdateCustomerBadData() {
        CustomerDto customerDto = getCustomerDto();
        customerDto.setCustomerName("");
        webTestClient
                .mutateWith(mockOAuth2Login())
                .put()
                .uri(CUSTOMER_PATH_ID, 1)
                .body(Mono.just(customerDto), CustomerDto.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(8)
    void testUpdateCustomerNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .put().uri(CUSTOMER_PATH_ID, 999)
                .body(Mono.just(getCustomerDto()), CustomerDto.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(9)
    void testPatchIdNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .patch().uri(CUSTOMER_PATH_ID, 999)
                .body(Mono.just(getCustomerDto()), CustomerDto.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(10)
    void testDeleteCustomer() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .delete()
                .uri(CUSTOMER_PATH_ID, 1)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(11)
    void testDeleteNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .delete().uri(CUSTOMER_PATH_ID, 999)
                .exchange()
                .expectStatus().isNotFound();
    }

    public static CustomerDto getCustomerDto() {
        return CustomerDto.builder()
                .customerName("Test Customer")
                .build();
    }
}
