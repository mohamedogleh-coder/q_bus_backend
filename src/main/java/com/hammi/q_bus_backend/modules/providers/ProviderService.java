package com.hammi.q_bus_backend.modules.providers;

import com.hammi.q_bus_backend.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProviderService {
    private final ProviderRepository providerRepository;

    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }

    public Provider getProviderById(Short providerId) {
        return providerRepository.findById(providerId).orElseThrow(() -> new NotFoundException("Provider not found"));
    }
}
