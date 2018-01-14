package com.example.customer.exceptions;

import com.example.customer.domains.Error;

import java.util.HashSet;

public class CustomerAlreadyExistsException extends BusinessException {

    private static final String MESSAGE = "Customer already exists in database.";
    private static final String FIELD = "customer";
    private static final String CODE = "customer.duplicated";

    public CustomerAlreadyExistsException() {
        super(MESSAGE, new HashSet<>());
        addError(new Error(MESSAGE, FIELD, CODE));
    }
}
