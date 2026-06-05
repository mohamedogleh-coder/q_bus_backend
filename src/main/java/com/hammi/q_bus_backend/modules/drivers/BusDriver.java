package com.hammi.q_bus_backend.modules.drivers;

import com.hammi.q_bus_backend.modules.buses.Bus;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "bus_driver",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "bus_driver_unq", columnNames = {"bus_id", "driver_phone"})
        }
)
public class BusDriver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "smallserial")
    private Short id;

    @NonNull
    @Column(name = "driver_phone", length = 20, nullable = false)
    private String driverPhone;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @Builder.Default
    @Column(name = "is_owner", nullable = false)
    private Boolean isOwner = true;

    @Builder.Default
    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;
}