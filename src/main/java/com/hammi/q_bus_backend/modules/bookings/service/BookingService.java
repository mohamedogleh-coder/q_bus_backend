package com.hammi.q_bus_backend.modules.bookings.service;

import com.hammi.q_bus_backend.exceptions.ApiException;
import com.hammi.q_bus_backend.exceptions.NotFoundException;
import com.hammi.q_bus_backend.modules.bookings.dao.BookSeatDto;
import com.hammi.q_bus_backend.modules.bookings.dao.QueueRequestDto;
import com.hammi.q_bus_backend.modules.bookings.dao.ScanSeatInformationDto;
import com.hammi.q_bus_backend.modules.bookings.entity.Booking;
import com.hammi.q_bus_backend.modules.bookings.entity.BookingSeat;
import com.hammi.q_bus_backend.modules.bookings.entity.QueueStatus;
import com.hammi.q_bus_backend.modules.bookings.repository.BookingRepository;
import com.hammi.q_bus_backend.modules.buses.BusRepository;
import com.hammi.q_bus_backend.modules.stations.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BusRepository busRepository;
    private final StationRepository stationRepository;

    public int addQueue(QueueRequestDto dto) {

        var isActiveOrWaiting = bookingRepository
                .findByBusIdAndStatusIn(dto.busId(), List.of(QueueStatus.active, QueueStatus.waiting)).isPresent();

        if (isActiveOrWaiting) {
            throw new ApiException("Bus-ku wuxuu ku jiraa waiting ama active booking");
        }

        var bus = busRepository.findById(dto.busId()).orElseThrow(() -> new NotFoundException("Bus not found"));
        var station = stationRepository.findById(dto.stationId()).orElseThrow(() -> new NotFoundException("Station not found"));

        try {

            var booking = Booking.builder()
                    .bus(bus)
                    .station(station)
                    .status(QueueStatus.waiting)
                    .build();

            var savedBooked = bookingRepository.save(booking);
            return savedBooked.getId();
        } catch (Exception e) {
            if (e.getMessage().contains("no_double_active_or_waiting_unq")) {
                throw new ApiException("Bus-ku wuxuu ku jiraa waiting ama active booking");
            } else {
                throw e;

            }
        }

    }

    public ScanSeatInformationDto scanSeat(UUID busId) {
        QueueStatus status = bookingRepository.getBookingStatus(busId).orElseThrow(() -> new NotFoundException("Bus is not ready for booking right now"));

        if (status.equals(QueueStatus.waiting)) {
            throw new ApiException("Bus ku wuxuu ku jira queue");
        }
        return
                bookingRepository.getBusInformationById(busId).orElseThrow(() -> new NotFoundException("Bus ku uma diyaar sana booking"));

    }


    public Integer bookSeat(Integer bookingId, BookSeatDto dto) {
        try {
            var booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Booking not found"));

            var seat = BookingSeat.builder().seatNumber(dto.seatNumber()).amountPaid(dto.amountPaid()).sentFrom(dto.sentFrom()).sentTo(dto.sentTo()).build();

            booking.getSeats().add(seat);

            seat.setBooking(booking);
            var savedBooking = bookingRepository.save(booking);
            return savedBooking.getId();
        } catch (Exception e) {
            if (e.getMessage().contains("verify_station_cost")) {
                throw new ApiException("Lacagta staanka iyo lacagta aad bixisay isku mid maha");
            } else {
                throw e;
            }
        }


    }
}
