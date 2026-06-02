package com.hammi.q_bus_backend.modules.buses;

import com.hammi.q_bus_backend.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/buses")
public class BusController {
    private final BusService busService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse<BusDTO>> registerBus(@Valid @RequestBody BusDAO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(busService.registerBus(request)));
    }

    @GetMapping("/{busId}")
    public ResponseEntity<ApiResponse<BusDTO>> getBusById(@PathVariable UUID busId) {
        return ResponseEntity.ok(new ApiResponse<>(busService.getBusById(busId)));
    }

    @GetMapping("/plate/{plateNumber}")
    public ResponseEntity<ApiResponse<BusDTO>> getBusByPlateNumber(@PathVariable String plateNumber) {
        return ResponseEntity.ok(new ApiResponse<>(busService.getBusByPlateNumber(plateNumber)));
    }
}
