package com.hammi.q_bus_backend.modules.print_documents;

import com.hammi.q_bus_backend.modules.print_documents.dto.PrintDocumentProjection;
import com.hammi.q_bus_backend.modules.print_documents.dto.PrintDocumentsDTO;
import com.hammi.q_bus_backend.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/documents")
public class PrintDocumentController {

    private final PrintDocumentService printDocumentService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<PrintDocumentsDTO>>> getDocumentsByStatus(@RequestParam(defaultValue = "pending") String status) {
        return ResponseEntity.ok(new ApiResponse<>(printDocumentService.getPrintDocumentList(status)));
    }

    @GetMapping("/{busId}")
    public ResponseEntity<ApiResponse<PrintDocumentProjection>> getDocumentByBusId(@PathVariable UUID busId) {
        return ResponseEntity.ok(new ApiResponse<>(printDocumentService.getDocument(busId)));
    }
}
