package com.example.spring6reactive.services;

import com.example.spring6reactive.mappers.BeerMapper;
import com.example.spring6reactive.model.BeerDto;
import com.example.spring6reactive.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    @Override
    public Flux<BeerDto> listBeers() {
        return beerRepository.findAll()
                .map(beerMapper::beerToBeerDto);
    }
}
