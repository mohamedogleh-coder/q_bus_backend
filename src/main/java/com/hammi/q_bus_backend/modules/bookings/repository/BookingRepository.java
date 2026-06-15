package com.hammi.q_bus_backend.modules.bookings.repository;

import com.hammi.q_bus_backend.modules.bookings.dao.ScanSeatInformationDto;
import com.hammi.q_bus_backend.modules.bookings.entity.Booking;
import com.hammi.q_bus_backend.modules.bookings.entity.QueueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Optional<Booking> findByBusIdAndStatusIn(UUID busId, Collection<QueueStatus> statuses);

    @Query("SELECT b.status FROM Booking b WHERE b.bus.id = :busId AND b.status IN (active,waiting)")
    Optional<QueueStatus> getBookingStatus(@Param("busId") UUID busId);

    @Query("SELECT new com.hammi.q_bus_backend.modules.bookings.dao.ScanSeatInformationDto(" +
            "b.id,c.categoryName, bb.plateNumber, s.stationName, s.stationCost, c.modelYear) " +
            "FROM Booking b " +
            "JOIN b.bus bb " +
            "JOIN bb.category c " +
            "JOIN b.station s " +
            "WHERE bb.id = :busId " +
            "AND b.status = com.hammi.q_bus_backend.modules.bookings.entity.QueueStatus.active")
    Optional<ScanSeatInformationDto> getBusInformationById(@Param("busId") UUID busId);
}
