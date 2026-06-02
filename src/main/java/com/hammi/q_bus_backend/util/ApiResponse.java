package com.hammi.q_bus_backend.util;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private T payload;
    private int status;

    public ApiResponse(T payload) {
        this.payload = payload;
        this.status = 1;
    }
}