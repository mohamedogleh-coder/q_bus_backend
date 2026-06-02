package com.hammi.q_bus_backend.modules.buses;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "bus_seats",
        uniqueConstraints = {
                @UniqueConstraint(name = "seat_unq", columnNames = {"seat_number", "bus_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Short id;

    @Column(name = "seat_number", nullable = false)
    private Short seatNumber;

    @Builder.Default
    @Column(name = "damaged", nullable = false)
    private Boolean damaged = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;
}