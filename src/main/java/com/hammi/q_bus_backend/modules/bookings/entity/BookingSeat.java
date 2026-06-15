package com.hammi.q_bus_backend.modules.bookings.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "booking_seats"
)
public class BookingSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(name = "seat_number", nullable = false)
    private Short seatNumber;

    @Column(name = "amount_paid", nullable = false, precision = 12, scale = 2)
    private BigDecimal amountPaid;

    @Column(name = "booking_time")
    private LocalDateTime bookingTime;

    @Column(name = "sent_from", nullable = false, length = 20)
    private String sentFrom;

    @Column(name = "sent_to", nullable = false, length = 20)
    private String sentTo;

    @PrePersist
    public void init() {
        bookingTime = LocalDateTime.now();
    }
}