package com.hammi.q_bus_backend.modules.print_documents.dto;

import org.springframework.beans.factory.annotation.Value;
import java.util.List;
import java.util.UUID;

public interface PrintDocumentProjection {

    UUID getId();
    String getStatus();

    @Value("#{target.bus.plateNumber}")
    String getPlateNumber();

    @Value("#{target.bus.steeringSide}")
    String getSteeringSide();

    @Value("#{target.bus.category.categoryName}")
    String getCategoryName();

    @Value("#{target.bus.category.numberOfSeats}")
    Short getNumberOfSeats();

    @Value("#{target.printedJets}")
    List<PrintJetsSummary> getPrintedJets();

    interface PrintJetsSummary {
         @Value("#{target.jet.jetName}")
        String getJetName();

        @Value("#{target.printDate}")
        java.time.LocalDateTime getPrintDate();
    }
}