package com.hammi.q_bus_backend.modules.bookings.dao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BookSeatDto(
        @NotNull(message = "Seat Number is required")
        Short seatNumber,
        @NotBlank(message = "Sent from is required")
        String sentFrom,
        @NotBlank(message = "Sent to is required")
        String sentTo,
        @NotNull(message = "Amount is required")
        BigDecimal amountPaid
) {
}
