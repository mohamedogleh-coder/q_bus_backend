package com.hammi.q_bus_backend.modules.bookings.dao;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ScanSeatInformationDto {
    private Integer bookingId;
    private String stationName;
    private BigDecimal stationCost;
    private String palateNumber;
    private String categoryName;
    private Integer modelYear;
    @JsonRawValue
    private String merchants;
}
