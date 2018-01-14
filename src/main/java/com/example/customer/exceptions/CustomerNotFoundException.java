package com.example.customer.exceptions;

import com.example.customer.domains.Error;

import java.util.HashSet;

public class CustomerNotFoundException extends BusinessException {

    private static final String MESSAGE = "Customer %s not found in database.";
    private static final String FIELD = "customer";
    private static final String CODE = "customer.notFound";

    public CustomerNotFoundException(final String email) {
        super(MESSAGE, new HashSet<>());
        addError(new Error(String.format(MESSAGE, email), FIELD, CODE));
    }
}