package com.hammi.q_bus_backend.modules.print_documents;

import com.hammi.q_bus_backend.exceptions.NotFoundException;
import com.hammi.q_bus_backend.modules.print_documents.dto.PrintDocumentProjection;
import com.hammi.q_bus_backend.modules.print_documents.dto.PrintDocumentsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrintDocumentService {
    private final PrintDocumentRepository printDocumentRepository;

    public List<PrintDocumentsDTO> getPrintDocumentList(@Param("status") String status) {
        return printDocumentRepository.getPrintDocumentList(status);
    }

    public PrintDocumentProjection getDocument(UUID busId) {
        return printDocumentRepository.getPrintDocumentByBus_Id(busId).orElseThrow(() -> new NotFoundException("Document not found"));
    }


//
//    public PrintDocumentDTO getDocument(UUID busId) {
//        var document = printDocumentRepository.getPrintDocumentByBusId(busId).orElseThrow(() -> new NotFoundException("Print Document not found"));
//
//        var bus = document.getBus();
//        var category = document.getBus().getCategory();
//
//        return new PrintDocumentDTO(bus.getId(), bus.getPlateNumber(), category.getNumberOfSeats(), category.getCategoryName(),
//                category.getModelYear(), bus.getSteeringSide(), category.getId(), document.getLastPrintedDate(), document.getNumOfPrinted());
//    }
}
