package com.hammi.q_bus_backend.exceptions;
public class NotFoundException extends RuntimeException {
    String message;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException() {
    }
}