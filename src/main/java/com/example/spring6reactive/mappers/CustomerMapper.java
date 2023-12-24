package com.example.spring6reactive.mappers;

import com.example.spring6reactive.domain.Customer;
import com.example.spring6reactive.model.CustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDto customerDto);
    CustomerDto customerToCustomerDto(Customer customer);
}
