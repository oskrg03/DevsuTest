package com.customer.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ClienteNotFoundException extends RuntimeException {

    private final HttpStatus status;
    public ClienteNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}