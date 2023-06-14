package com.currencyExchange.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class Exceptions extends ResponseStatusException {

    public Exceptions(String reason) {
        super(HttpStatus.CONFLICT, reason);
    }
}
