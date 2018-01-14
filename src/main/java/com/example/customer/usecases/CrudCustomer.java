package com.example.customer.usecases;

import com.example.customer.domains.Customer;
import com.example.customer.exceptions.CustomerAlreadyExistsException;
import com.example.customer.exceptions.CustomerNotFoundException;
import com.example.customer.gateways.CustomerGateway;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class CrudCustomer {

    private CustomerGateway customerGateway;

    @Autowired
    public CrudCustomer(CustomerGateway customerGateway) {
        this.customerGateway = customerGateway;
    }

    private void createUuidForAddresses(final Customer customer) {
        if (CollectionUtils.isNotEmpty(customer.getAddresses())) {
            customer.getAddresses()
                    .stream()
                    .forEach(address -> address.setId(UUID.randomUUID().toString()));
        }
    }

    public Customer create(final Customer customer) {
        log.debug("Creating a new customer {}", customer);

        Optional<Customer> customerOptional = customerGateway.findByDocument(customer.getDocument());
        if (customerOptional.isPresent()) {
            throw new CustomerAlreadyExistsException();
        }

        customer.setUuid(null);
        createUuidForAddresses(customer);

        return customerGateway.save(customer);
    }

    public Customer update(final Customer customer) {
        log.debug("Updating customer {}", customer);

        Optional<Customer> customerOptional = customerGateway.findByDocument(customer.getDocument());
        if (!customerOptional.isPresent()) {
            throw new CustomerNotFoundException(customer.getDocument());
        }

        return customerGateway.save(customer);
    }

    public Customer findByDocument(String document) {
        log.debug("Finding customer by document {}", document);
        return customerGateway.findByDocument(document).orElseThrow(() -> new CustomerNotFoundException(document));
    }

    public List<Customer> findAll() {
        log.debug("Find all customers");
        return customerGateway.findAll();
    }

    public void delete(String document) {
        log.debug("Deleting customer with document {}", document);

        customerGateway
                .findByDocument(document)
                .ifPresent(customer -> customerGateway.delete(customer));
    }
}
