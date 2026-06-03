package com.hammi.q_bus_backend.modules.print_documents.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "print_jet")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrintJet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "jet_name", nullable = false, length = 50)
    private String jetName;
}
