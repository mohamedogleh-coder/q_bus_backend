package com.hammi.q_bus_backend.modules.buses;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "providers")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "provider_name", nullable = false, unique = true, length = 20)
    private String providerName;

    @Column(name = "provider_service", nullable = false, length = 20)
    private String providerService;
}