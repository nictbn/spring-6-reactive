package com.example.spring6reactive.controllers;

import com.example.spring6reactive.model.BeerDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class BeerController {
    public static final String BEER_PATH = "/api/v2/beer";

    @GetMapping(BEER_PATH)
    Flux<BeerDto> listBeers() {
        return Flux.just(BeerDto.builder().id(1).build(), BeerDto.builder().id(2).build());
    }
}
