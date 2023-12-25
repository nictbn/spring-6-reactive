package com.example.spring6reactive.controllers;

import com.example.spring6reactive.domain.Beer;
import com.example.spring6reactive.model.BeerDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.example.spring6reactive.controllers.BeerController.BEER_PATH;
import static com.example.spring6reactive.controllers.BeerController.BEER_PATH_ID;
import static com.example.spring6reactive.repositories.BeerRepositoryTest.getTestBeer;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BeerControllerTest {

    public static final String CONTENT_TYPE = "Content-type";
    public static final String APPLICATION_JSON = "application/json";
    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(1)
    void testListBeers() {
        webTestClient.get().uri(BEER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals(CONTENT_TYPE, APPLICATION_JSON)
                .expectBody().jsonPath("$.size()").isEqualTo(3);
    }

    @Test
    @Order(2)
    void testGetById() {
        webTestClient.get().uri(BEER_PATH_ID, 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals(CONTENT_TYPE, APPLICATION_JSON)
                .expectBody(BeerDto.class);
    }

    @Test
    @Order(3)
    void testGetByIdNotFound() {
        webTestClient.get().uri(BEER_PATH_ID, 999)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(4)
    void testCreateBeer() {
        webTestClient.post().uri(BEER_PATH)
                .body(Mono.just(getTestBeer()), BeerDto.class)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/v2/beer/4");
    }

    @Test
    @Order(5)
    void testCreateBeerBadData() {
        Beer testBeer = getTestBeer();
        testBeer.setBeerName("");
        webTestClient.post().uri(BEER_PATH)
                .body(Mono.just(testBeer), BeerDto.class)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(6)
    void testUpdateBeer() {
        webTestClient.put().uri(BEER_PATH_ID, 1)
                .body(Mono.just(getTestBeer()), BeerDto.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(7)
    void testUpdateBeerBad() {
        Beer testBeer = getTestBeer();
        testBeer.setBeerStyle("");
        webTestClient.put().uri(BEER_PATH_ID, 1)
                .body(Mono.just(testBeer), BeerDto.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(8)
    void testDeleteBeer() {
        webTestClient.delete().uri(BEER_PATH_ID, 1)
                .exchange()
                .expectStatus()
                .isNoContent();
    }
}