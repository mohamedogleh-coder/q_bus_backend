package com.hammi.q_bus_backend.modules.bookings;

import com.hammi.q_bus_backend.modules.bookings.dao.BookSeatDto;
import com.hammi.q_bus_backend.modules.bookings.dao.QueueRequestDto;
import com.hammi.q_bus_backend.modules.bookings.dao.ScanSeatInformationDto;
 import com.hammi.q_bus_backend.modules.bookings.service.BookingService;
import com.hammi.q_bus_backend.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/queue")
    public ResponseEntity<ApiResponse<Integer>> addQueue(@Valid @RequestBody QueueRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(bookingService.addQueue(dto)));
    }

    @GetMapping("/{busId}/scan")
    public ResponseEntity<ApiResponse<ScanSeatInformationDto>> scanSeat(@PathVariable UUID busId) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(bookingService.scanSeat(busId)));
    }


    @PostMapping("/{bookingId}/book")
    public ResponseEntity<ApiResponse<Integer>> scanSeat(@PathVariable Integer bookingId, @Valid @RequestBody BookSeatDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(bookingService.bookSeat(bookingId,dto)));
    }
}
