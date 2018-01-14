package com.example.customer.exceptions;

import com.example.customer.domains.Error;

import java.util.Set;

public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final Set<Error> errors;

    public BusinessException(final String message, final Set<Error> errors) {
        super(message);
        this.errors = errors;
    }

    public void addError(Error error) {
        this.errors.add(error);
    }

    public Set<Error> getErrors() {
        return this.errors;
    }
}