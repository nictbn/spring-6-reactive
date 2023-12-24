package com.example.spring6reactive.services;

import com.example.spring6reactive.mappers.CustomerMapper;
import com.example.spring6reactive.model.CustomerDto;
import com.example.spring6reactive.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Flux<CustomerDto> listCustomers() {
        return customerRepository.findAll()
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<CustomerDto> getCustomerById(Integer customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<CustomerDto> saveNewCustomer(CustomerDto CustomerDto) {
        return customerRepository.save(customerMapper.customerDtoToCustomer(CustomerDto))
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<CustomerDto> updateCustomer(Integer customerId, CustomerDto CustomerDto) {
        return customerRepository.findById(customerId)
                .map(customer -> {
                    customer.setCustomerName(CustomerDto.getCustomerName());
                    return customer;
                }).flatMap(customerRepository::save)
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<CustomerDto> patchCustomer(Integer customerId, CustomerDto CustomerDto) {
        return customerRepository.findById(customerId)
                .map(customer -> {
                    if (StringUtils.hasText(CustomerDto.getCustomerName())){
                        customer.setCustomerName(CustomerDto.getCustomerName());
                    }
                    return customer;
                }).flatMap(customerRepository::save)
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<Void> deleteCustomerById(Integer customerId) {
        return customerRepository.deleteById(customerId);
    }
}
