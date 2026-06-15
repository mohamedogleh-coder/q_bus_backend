package com.hammi.q_bus_backend.modules.bookings.dao;

import java.math.BigDecimal;

public record ScanSeatInformationDto(
        String categoryName,
        String plateNumber,
        String stationName,
        BigDecimal stationCost,
        String modelYear
 ) {
}
