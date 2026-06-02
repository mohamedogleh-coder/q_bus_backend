package com.hammi.q_bus_backend.exceptions;


public class ApiException extends RuntimeException {
    private String message;

    public ApiException(String message) {
        super(message);
    }
}
