package com.example.customer.http;

import com.example.customer.domains.Customer;
import com.example.customer.usecases.CrudCustomer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/customers")
@Api(value = "/customers", tags = "Customers management",
        produces = APPLICATION_JSON_VALUE,
        consumes = APPLICATION_JSON_VALUE)
public class CustomerController {

    private CrudCustomer crudCustomer;

    @Autowired
    public CustomerController(CrudCustomer crudCustomer) {
        this.crudCustomer = crudCustomer;
    }

    @ApiOperation(value = "Create a new customer")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Customer created", response = Customer.class),
            @ApiResponse(code = 400, message = "Invalid customer"),
            @ApiResponse(code = 422, message = "Customer already exists")
    })
    @RequestMapping(method = RequestMethod.POST,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> create(@Valid @RequestBody final Customer customer) {
        log.info("Received customer: {}.", customer);
        final Customer newCustomer = crudCustomer.create(customer);
        log.info("UUID Generated: {}", newCustomer.getUuid());

        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update a customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customer updated", response = Customer.class),
            @ApiResponse(code = 400, message = "Invalid customer"),
            @ApiResponse(code = 404, message = "Customer not found")
    })
    @RequestMapping(method = RequestMethod.PUT,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> update(@Valid @RequestBody final Customer customer) {
        log.info("Updating customer: {}", customer);
        final Customer updatedCustomer = crudCustomer.update(customer);

        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @ApiOperation(value = "Get customer by uuid")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Customer found"),
            @ApiResponse(code = 404, message = "Customer not found")
    })
    @RequestMapping(value = "/{document}",
            method = RequestMethod.GET,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> findByDocument(@PathVariable("document") final String document) {
        log.info("Getting customer with document: {}", document);
        final Customer customer = crudCustomer.findByDocument(document);

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @ApiOperation(value = "Get all customers")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Customers found"),
            @ApiResponse(code = 404, message = "Customers not found")
    })
    @RequestMapping(method = RequestMethod.GET,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Customer>> findAll() {
        log.info("Getting all customers");
        final List<Customer> customers = crudCustomer.findAll();

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete customer by document")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Customers found")
    })
    @ResponseStatus(NO_CONTENT)
    @RequestMapping(value = "/{document}",
            method = RequestMethod.DELETE,
            produces = APPLICATION_JSON_VALUE)
    public void deleteByEmail(@PathVariable("document") final String document) {
        log.info("Deleting customer with document: {}", document);
        crudCustomer.delete(document);
    }
}
