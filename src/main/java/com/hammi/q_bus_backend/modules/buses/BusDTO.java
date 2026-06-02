package com.hammi.q_bus_backend.modules.buses;

import java.util.UUID;

public record BusDTO(
        UUID busId,
        String plateNumber,
        int numberOfSeats,
        String categoryName,
        String modelYear,
        SteeringSide steeringSide,
        Short categoryId
) {
}
