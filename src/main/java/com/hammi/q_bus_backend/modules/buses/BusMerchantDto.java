package com.hammi.q_bus_backend.modules.buses;

public record BusMerchantDto(
        short merchantId,
        String merchantNumber,
        String providerName,
        String providerService
) {
}
