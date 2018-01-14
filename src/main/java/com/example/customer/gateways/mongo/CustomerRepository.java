package com.example.customer.gateways.mongo;

import com.example.customer.domains.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    Customer findOneByDocument(String document);
}
