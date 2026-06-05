package com.hammi.q_bus_backend.modules.buses;

import com.hammi.q_bus_backend.modules.drivers.BusDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusRepository extends JpaRepository<Bus, UUID> {

    @Query("SELECT b FROM Bus b JOIN FETCH b.category WHERE b.id = :uuid")
    Optional<Bus> getBusWithCategoryById(@Param("uuid") UUID uuid);

    @Query("SELECT b FROM Bus b JOIN FETCH b.category WHERE b.plateNumber = :plateNumber")
    Optional<Bus> getBusWithCategoryByPlateNumber(@Param("plateNumber") String plateNumber);

    @Query("SELECT b from Bus b JOIN FETCH b.busDrivers bd WHERE b.id=:busId")
    Optional<Bus> getBusDriversByBusId(@Param("busId") UUID busId);
}
