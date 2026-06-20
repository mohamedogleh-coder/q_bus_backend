package com.hammi.q_bus_backend.modules.buses;

import com.hammi.q_bus_backend.modules.providers.Provider;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "bus_merchants",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "bus_merchant_unq",
                        columnNames = {"bus_id", "provider_id", "merchant_number"}
                )
        }
)
public class BusMerchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @Column(name = "merchant_number", nullable = false, length = 20)
    private String merchantNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;
}