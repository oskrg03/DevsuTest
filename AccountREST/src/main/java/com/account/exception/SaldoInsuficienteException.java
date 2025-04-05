package com.account.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SaldoInsuficienteException extends RuntimeException {

    private final HttpStatus status;
    public SaldoInsuficienteException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}