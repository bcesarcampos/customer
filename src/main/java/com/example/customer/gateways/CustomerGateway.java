package com.example.customer.gateways;

import com.example.customer.domains.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerGateway {

    Customer save(Customer customer);

    Optional<Customer> findByDocument(String document);

    List<Customer> findAll();

    void delete(Customer customer);
}
