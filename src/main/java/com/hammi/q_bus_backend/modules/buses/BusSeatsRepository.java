package com.hammi.q_bus_backend.modules.buses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BusSeatsRepository extends JpaRepository<BusSeat, UUID> {
}
