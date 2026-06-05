package com.hammi.q_bus_backend.modules.drivers;

public record BusDriverDTO(
        String driverName,
        String driverPhoneNumber,
        String profileUrl,
        boolean isOwner,
        boolean isDefault
) {
}
