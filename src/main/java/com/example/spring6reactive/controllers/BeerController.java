package com.example.spring6reactive.controllers;

import com.example.spring6reactive.model.BeerDto;
import com.example.spring6reactive.services.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class BeerController {
    public static final String BEER_PATH = "/api/v2/beer";

    private final BeerService beerService;

    @GetMapping(BEER_PATH)
    Flux<BeerDto> listBeers() {
        return beerService.listBeers();
    }
}
