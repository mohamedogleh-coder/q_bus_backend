package com.hammi.q_bus_backend.modules.buses;

import com.hammi.q_bus_backend.modules.drivers.BusDriverDTO;
import com.hammi.q_bus_backend.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PutMapping("/{busId}")
    public ResponseEntity<ApiResponse<BusDTO>> updateBus(@Valid @RequestBody BusDAO request, @PathVariable UUID busId) {
        return ResponseEntity.ok(new ApiResponse<>(busService.updateBus(request, busId)));
    }

    @GetMapping("/{busId}")
    public ResponseEntity<ApiResponse<BusDTO>> getBusById(@PathVariable UUID busId) {
        return ResponseEntity.ok(new ApiResponse<>(busService.getBusById(busId)));
    }

    @GetMapping("/plate/{plateNumber}")
    public ResponseEntity<ApiResponse<BusDTO>> getBusByPlateNumber(@PathVariable String plateNumber) {
        return ResponseEntity.ok(new ApiResponse<>(busService.getBusByPlateNumber(plateNumber)));
    }

    @GetMapping("/{busId}/drivers")
    public ResponseEntity<ApiResponse<List<BusDriverDTO>>> getBusDrivers(@PathVariable UUID busId) {
        return ResponseEntity.ok(new ApiResponse<>(busService.getBusDrivers(busId)));
    }


    @GetMapping("/{busId}/merchants")
    public ResponseEntity<ApiResponse<List<BusMerchantDto>>> getBusMerchants(@PathVariable UUID busId) {
        return ResponseEntity.ok(new ApiResponse<>(busService.getBusMerchants(busId)));
    }

    @PostMapping("/{busId}/merchants")
    public ResponseEntity<ApiResponse<Short>> addBusMerchant(@PathVariable UUID busId, @Valid @RequestBody AddBusMerchantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(busService.addBusMerchant(busId, request)));
    }


    @PutMapping("/{busId}/merchants/{merchantId}")
    public ResponseEntity<ApiResponse<Short>> addBusMerchant(@PathVariable UUID busId, @PathVariable Short merchantId, @Valid @RequestBody AddBusMerchantRequest request) {
        return ResponseEntity.ok().body(new ApiResponse<>(busService.updateBusMerchant(busId, merchantId, request)));
    }


    @DeleteMapping("/{busId}/merchants/{merchantId}")
    public ResponseEntity<ApiResponse<Short>> deleteMerchant(@PathVariable UUID busId, @PathVariable Short merchantId) {
        busService.deleteBusMerchant(busId, merchantId);
        return ResponseEntity.ok().body(new ApiResponse<>(null));
    }
}
