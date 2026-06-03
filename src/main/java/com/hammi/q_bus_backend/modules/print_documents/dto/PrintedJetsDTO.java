package com.hammi.q_bus_backend.modules.print_documents.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PrintedJetsDTO(
        String jetName,
        LocalDateTime printDate
) {
}
