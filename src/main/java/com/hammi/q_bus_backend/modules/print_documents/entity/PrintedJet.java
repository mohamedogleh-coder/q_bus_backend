package com.hammi.q_bus_backend.modules.print_documents.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "printed_jets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrintedJet {

    @EmbeddedId
    private PrintedJetKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("documentId")
    @JoinColumn(name = "document_id", nullable = false)
    private PrintDocument document;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("jetId") // Waxay ku xiraysaa jetId-ga ku jira Key-ga dusha
    @JoinColumn(name = "jet_id", nullable = false)
    private PrintJet jet;

    @Column(name = "print_date", nullable = false)
    private LocalDateTime printDate;

    @PrePersist
    protected void onCreate() {
        if (this.printDate == null) {
            this.printDate = LocalDateTime.now();
        }
    }
}