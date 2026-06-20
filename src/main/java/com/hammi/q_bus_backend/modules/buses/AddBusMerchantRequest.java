package com.hammi.q_bus_backend.modules.buses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddBusMerchantRequest(
        @NotBlank(message = "Merchant number is required")
        String merchantNumber,
        @NotNull(message = "Provider id is required")
        Short providerId
) {
}
