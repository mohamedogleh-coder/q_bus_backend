package com.hammi.q_bus_backend.modules.buses;

import com.hammi.q_bus_backend.exceptions.ApiException;
import com.hammi.q_bus_backend.exceptions.NotFoundException;
import com.hammi.q_bus_backend.modules.categories.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BusService {
    private final BusRepository busRepository;
    private final CategoryRepository categoryRepository;


    public BusDTO registerBus(BusDAO request) {
        try {
            var category = categoryRepository.findById(request.categoryId()).orElseThrow(() -> new NotFoundException("Category not found"));

            var bus = Bus.builder()
                    .plateNumber(request.plateNumber())
                    .steeringSide(request.steeringSide())
                    .category(category)
                    .build();

            busRepository.save(bus);
            return new BusDTO(bus.getId(), bus.getPlateNumber(), category.getNumberOfSeats(),
                    category.getCategoryName(), category.getModelYear(), bus.getSteeringSide(), category.getId());
        } catch (Exception e) {
            if (e.getMessage().contains("plate_number_unq")) {
                throw new ApiException("This is plate number already exists.");
            } else {
                throw new ApiException(e.getMessage());
            }
        }

    }

    @Transactional
    public BusDTO updateBus(BusDAO request, UUID busId) {
        try {
            var bus = busRepository.getBusWithCategoryById(busId)
                    .orElseThrow(() -> new NotFoundException("Bus not found"));

            bus.setPlateNumber(request.plateNumber());
            bus.setSteeringSide(request.steeringSide());

            if (!bus.getCategory().getId().equals(request.categoryId())) {
                var category = categoryRepository.findById(request.categoryId())
                        .orElseThrow(() -> new NotFoundException("Category not found"));
                bus.setCategory(category);
            }

            busRepository.save(bus);

            return new BusDTO(
                    bus.getId(),
                    bus.getPlateNumber(),
                    bus.getCategory().getNumberOfSeats(),
                    bus.getCategory().getCategoryName(),
                    bus.getCategory().getModelYear(),
                    bus.getSteeringSide(),
                    bus.getCategory().getId()
            );

        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("plate_number_unq")) {
                throw new ApiException("This plate number already exists.");
            } else {
                throw new ApiException(e.getMessage());
            }
        }
    }

    public BusDTO getBusById(UUID busId) {
        var bus = busRepository.getBusWithCategoryById(busId).orElseThrow(() -> new NotFoundException("Bus not found"));
        return new BusDTO(bus.getId(), bus.getPlateNumber(), bus.getCategory().getNumberOfSeats(),
                bus.getCategory().getCategoryName(), bus.getCategory().getModelYear(), bus.getSteeringSide(), bus.getCategory().getId());
    }

    public BusDTO getBusByPlateNumber(String plateNumber) {
        var bus = busRepository.getBusWithCategoryByPlateNumber(plateNumber).orElseThrow(() -> new NotFoundException("Bus not found"));
        return new BusDTO(bus.getId(), bus.getPlateNumber(), bus.getCategory().getNumberOfSeats(),
                bus.getCategory().getCategoryName(), bus.getCategory().getModelYear(), bus.getSteeringSide(), bus.getCategory().getId());
    }
}
