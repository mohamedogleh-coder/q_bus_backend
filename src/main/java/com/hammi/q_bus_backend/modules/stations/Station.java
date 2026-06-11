package com.hammi.q_bus_backend.modules.stations;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stations")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Short id;

    @Column(name = "station_name", nullable = false, unique = true, length = 100)
    private String stationName;

    @Column(name = "station_cost", nullable = false, precision = 12, scale = 2)
    private BigDecimal stationCost;
}
