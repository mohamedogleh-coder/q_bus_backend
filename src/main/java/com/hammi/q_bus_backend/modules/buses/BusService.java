package com.hammi.q_bus_backend.modules.buses;

import com.hammi.q_bus_backend.exceptions.ApiException;
import com.hammi.q_bus_backend.exceptions.NotFoundException;
import com.hammi.q_bus_backend.modules.categories.CategoryRepository;
import com.hammi.q_bus_backend.modules.drivers.BusDriver;
import com.hammi.q_bus_backend.modules.drivers.BusDriverDTO;
import com.hammi.q_bus_backend.modules.providers.ProviderRepository;
import com.hammi.q_bus_backend.supabase.appuser.AppUser;
import com.hammi.q_bus_backend.supabase.appuser.AppUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BusService {
    private final BusRepository busRepository;
    private final CategoryRepository categoryRepository;
    private final AppUserRepository userRepository;
    private final ProviderRepository providerRepository;
    private final BusMerchantsRepository busMerchantsRepository;
    final JdbcTemplate jdbcTemplate;


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

    public List<BusDriverDTO> getBusDrivers(UUID busId) {
        var bus = busRepository.getBusDriversByBusId(busId).orElseThrow(() -> new NotFoundException("Bus not found"));

        if (bus.getBusDrivers().isEmpty()) {
            return List.of();
        }

        var phoneNumbers = bus.getBusDrivers().stream()
                .map(BusDriver::getDriverPhone)
                .toList();

        var appUsersList = userRepository.findAllByPhoneNumberIn(phoneNumbers);


        java.util.Map<String, AppUser> userMap = appUsersList.stream()
                .collect(java.util.stream.Collectors.toMap(
                        AppUser::getPhoneNumber,
                        user -> user
                ));

        return bus.getBusDrivers().stream().map(bd -> {
            AppUser user = userMap.get(bd.getDriverPhone());

            String fullName = (user != null) ? (user.getFirstName() + " " + user.getLastName()).trim() : "Unknown Driver";
            String profileUrl = (user != null) ? user.getProfileUrl() : null;

            return new BusDriverDTO(
                    fullName,
                    bd.getDriverPhone(),
                    profileUrl,
                    bd.getIsOwner(),
                    bd.getIsDefault()
            );
        }).toList();
    }


    public List<BusMerchantDto> getBusMerchants(UUID busId) {
        var bus = busRepository.getBusMerchantsByBusId(busId).orElseThrow(() -> new NotFoundException("Bus Not found"));

        return bus.getMerchants().stream().map((merchant) -> {
            var provider = merchant.getProvider();
            return new BusMerchantDto(merchant.getId(), merchant.getMerchantNumber(),
                    provider.getProviderName(), provider.getProviderService());
        }).toList();
    }


    public Short addBusMerchant(UUID busId, AddBusMerchantRequest request) {

        var bus = busRepository.getBusMerchantsByBusId(busId).orElseThrow(() -> new NotFoundException("Bus Not found"));

        boolean isExists = bus.getMerchants()
                .stream()
                .anyMatch((merchant ->
                        merchant.getMerchantNumber().equals(request.merchantNumber()) && merchant.getProvider()
                                .getId().equals(request.providerId())));


        if (isExists) throw new ApiException("This merchant already exists");

        var provider = providerRepository.findById(request.providerId()).orElseThrow(() -> new NotFoundException("Provider not exists"));

        var merchant = BusMerchant.builder()
                .provider(provider).bus(bus).merchantNumber(request.merchantNumber()).build();

        var savedMerchant = busMerchantsRepository.save(merchant);
        return savedMerchant.getId();
    }


    public Short updateBusMerchant(UUID busId, Short merchantId, AddBusMerchantRequest request) {
        var bus = busRepository.getBusMerchantsByBusId(busId).orElseThrow(() -> new NotFoundException("Bus Not found"));

        var existedMerchant = bus.getMerchants().stream().filter(merchant -> merchant.getId().equals(merchantId)).findFirst().orElseThrow(() -> new NotFoundException("Merchant not found"));
        existedMerchant.setMerchantNumber(request.merchantNumber());

        if (!existedMerchant.getProvider().getId().equals(request.providerId())) {
            var provider = providerRepository.findById(request.providerId()).orElseThrow(() -> new NotFoundException("Provider not exists"));
            existedMerchant.setProvider(provider);
        }
        busRepository.save(bus);
        return existedMerchant.getId();
    }


    public void deleteBusMerchant(UUID busId, Short merchantId) {
        var bus = busRepository.getBusMerchantsByBusId(busId).orElseThrow(() -> new NotFoundException("Bus Not found"));

        var deleteMerchant = bus.getMerchants().stream().filter(merchant -> merchant.getId().equals(merchantId)).findFirst().orElseThrow(() -> new NotFoundException("Merchant not found"));

        bus.getMerchants().remove(deleteMerchant);
        busRepository.save(bus);
    }

}
