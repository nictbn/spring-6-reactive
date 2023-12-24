package com.example.spring6reactive.services;

import com.example.spring6reactive.model.BeerDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BeerService {
    Flux<BeerDto> listBeers();

    Mono<BeerDto> getBeerById(Integer beerId);
}
