package com.example.customer.gateways.mongo;

import com.example.customer.domains.Customer;
import com.example.customer.gateways.CustomerGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerGatewayImpl implements CustomerGateway {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerGatewayImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> findByDocument(String document) {
        return Optional.ofNullable(customerRepository.findOneByDocument(document));
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public void delete(final Customer customer) {
        customerRepository.delete(customer);
    }
}
