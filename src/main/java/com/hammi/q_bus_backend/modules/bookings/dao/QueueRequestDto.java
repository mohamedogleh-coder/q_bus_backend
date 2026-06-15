package com.hammi.q_bus_backend.modules.bookings.dao;

import com.hammi.q_bus_backend.modules.bookings.entity.QueueStatus;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record QueueRequestDto(
        @NotNull(message = "Bus ID is required")
        UUID busId,
        @NotNull(message = "Station is required")
        Short stationId,
        QueueStatus status
) {
}