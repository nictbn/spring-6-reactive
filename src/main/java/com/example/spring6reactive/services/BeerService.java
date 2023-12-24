package com.example.spring6reactive.services;

import com.example.spring6reactive.model.BeerDto;
import reactor.core.publisher.Flux;

public interface BeerService {
    Flux<BeerDto> listBeers();
}
