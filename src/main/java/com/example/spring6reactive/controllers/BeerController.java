package com.example.spring6reactive.controllers;

import com.example.spring6reactive.model.BeerDto;
import com.example.spring6reactive.services.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class BeerController {
    public static final String BEER_PATH = "/api/v2/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    private final BeerService beerService;

    @GetMapping(BEER_PATH)
    Flux<BeerDto> listBeers() {
        return beerService.listBeers();
    }

    @GetMapping(BEER_PATH_ID)
    Mono<BeerDto> getBeerById(@PathVariable("beerId") Integer beerId) {
        return beerService.getBeerById(beerId);
    }

    @PostMapping(BEER_PATH)
    Mono<ResponseEntity<Void>> createNewBeer(@RequestBody BeerDto beerDto) {
        return beerService.saveNewBeer(beerDto)
                .map(savedDto -> ResponseEntity.created(UriComponentsBuilder
                                .fromHttpUrl("http://localhost:8080/" + BEER_PATH + "/" + savedDto.getId()).build().toUri())
                        .build());
    }

    Mono<ResponseEntity<Void>> updateExistingBeer(@PathVariable("beerId") Integer beerId, @RequestBody BeerDto beerDto) {
        beerService.updateBeer(beerId, beerDto).subscribe();
        return Mono.just(ResponseEntity.ok().build());
    }
}
