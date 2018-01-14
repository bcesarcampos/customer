package com.example.customer.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { CustomerAlreadyExistsException.class })
    protected ResponseEntity<Object> handleBusinessException(CustomerAlreadyExistsException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getErrors(),
                new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(value = { CustomerNotFoundException.class })
    public ResponseEntity<Object> resourceNotFound(CustomerNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getErrors(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}