package com.hammi.q_bus_backend.modules.print_documents.dto;

import com.hammi.q_bus_backend.modules.buses.SteeringSide;

import java.util.UUID;

public record PrintDocumentsDTO(
        UUID busId,
        String plateNumber,
        int numberOfSeats,
        String categoryName,
        SteeringSide steeringSide
) {
}
