package com.hammi.q_bus_backend.modules.buses;

import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusRepository extends JpaRepository<Bus, UUID> {
    @Override
    boolean existsById(@Param("busId") UUID busId);

    @Query("SELECT b FROM Bus b JOIN FETCH b.category WHERE b.id = :uuid")
    Optional<Bus> getBusWithCategoryById(@Param("uuid") UUID uuid);

    @Query("SELECT b FROM Bus b JOIN FETCH b.category WHERE b.plateNumber = :plateNumber")
    Optional<Bus> getBusWithCategoryByPlateNumber(@Param("plateNumber") String plateNumber);

    @Query("SELECT b from Bus b JOIN FETCH b.busDrivers bd WHERE b.id=:busId")
    Optional<Bus> getBusDriversByBusId(@Param("busId") UUID busId);

    @Query("SELECT b from Bus b JOIN FETCH b.merchants bd JOIN FETCH bd.provider WHERE b.id=:busId")
    Optional<Bus> getBusMerchantsByBusId(@Param("busId") UUID busId);
}
