package com.hammi.q_bus_backend.modules.stations;

import com.hammi.q_bus_backend.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StationService {
    private final StationRepository stationRepository;

    public List<Station> getStations() {
        return stationRepository.findAll();
    }

    public Station getStation(Short stationId) {
        return stationRepository.findById(stationId).orElseThrow(() -> new NotFoundException("Station not found"));
    }
}
