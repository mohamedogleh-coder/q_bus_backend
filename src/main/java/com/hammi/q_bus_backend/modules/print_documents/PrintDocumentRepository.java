package com.hammi.q_bus_backend.modules.print_documents;

import com.hammi.q_bus_backend.modules.print_documents.dto.PrintDocumentProjection;
import com.hammi.q_bus_backend.modules.print_documents.dto.PrintDocumentsDTO;
import com.hammi.q_bus_backend.modules.print_documents.entity.PrintDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrintDocumentRepository extends JpaRepository<PrintDocument, Short> {

    @Query("SELECT new com.hammi.q_bus_backend.modules.print_documents.dto.PrintDocumentsDTO(" +
            "d.id, b.plateNumber, c.numberOfSeats, c.categoryName, b.steeringSide) " +
            "FROM PrintDocument d " +
            "JOIN d.bus b " +
            "JOIN b.category c " +
            "WHERE d.status = :status")
    List<PrintDocumentsDTO> getPrintDocumentList(@Param("status") String status);

    @Query("SELECT d FROM PrintDocument d LEFT JOIN FETCH d.printedJets p LEFT JOIN FETCH p.jet JOIN FETCH d.bus b JOIN FETCH b.category where b.id=:busId")
    Optional<PrintDocumentProjection> getPrintDocumentByBus_Id(@Param("busId")UUID busId);

    //    UUID busId,
//    String plateNumber,
//    int numberOfSeats,
//    String categoryName,
//    SteeringSide steeringSide
//
//
//    @Query("SELECT d FROM PrintDocument d JOIN FETCH d.bus b JOIN FETCH b.category where b.id=:busId")
//    Optional<PrintDocument> getPrintDocumentByBusId(@Param("busId") UUID busId);
}
