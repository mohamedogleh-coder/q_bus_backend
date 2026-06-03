package com.hammi.q_bus_backend.modules.print_documents.entity;

import com.hammi.q_bus_backend.modules.buses.Bus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "print_documents")
public class PrintDocument {
    @Id
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Bus bus;

    @Column(nullable = false, length = 10)
    private String status = "pending";

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrintedJet> printedJets;
}
