package com.hammi.q_bus_backend.modules.buses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BusDAO(
        @NotBlank(message = "Plate number is required")
        String plateNumber,
        @NotNull(message = "Steering side is required")
        SteeringSide steeringSide,
        @NotNull(message = "Category id is required")
        Short categoryId
        ) {
}
