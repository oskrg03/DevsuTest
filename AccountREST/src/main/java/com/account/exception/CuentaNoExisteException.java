package com.account.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CuentaNoExisteException extends RuntimeException {

    private final HttpStatus status;
    public CuentaNoExisteException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}