package com.hammi.q_bus_backend.modules.stations;

import com.hammi.q_bus_backend.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/stations")
@RequiredArgsConstructor
class StationController {
    public final StationService stationService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<Station>>> getStations() {
        return ResponseEntity.ok(new ApiResponse<>(stationService.getStations()));
    }

    @GetMapping("/{stationId}")
    public ResponseEntity<ApiResponse<Station>> getStation(@PathVariable Short stationId) {
        return ResponseEntity.ok(new ApiResponse<>(stationService.getStation(stationId)));
    }
}
