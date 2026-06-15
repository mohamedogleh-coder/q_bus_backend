package com.hammi.q_bus_backend.modules.bookings.repository;

import com.hammi.q_bus_backend.modules.bookings.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingSeatsRepository extends JpaRepository<Booking, Integer> {
}
