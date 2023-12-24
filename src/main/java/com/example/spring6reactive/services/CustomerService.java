package com.example.spring6reactive.services;

import com.example.spring6reactive.model.CustomerDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Flux<CustomerDto> listCustomers();

    Mono<CustomerDto> getCustomerById(Integer customerId);

    Mono<CustomerDto> saveNewCustomer(CustomerDto customerDto);

    Mono<CustomerDto> updateCustomer(Integer customerId, CustomerDto customerDto);

    Mono<CustomerDto> patchCustomer(Integer customerId, CustomerDto customerDto);

    Mono<Void> deleteCustomerById(Integer customerId);
}
