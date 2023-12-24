package com.example.spring6reactive.mappers;

import com.example.spring6reactive.domain.Beer;
import com.example.spring6reactive.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDto dto);
    BeerDto beerToBeerDto(Beer beer);
}
