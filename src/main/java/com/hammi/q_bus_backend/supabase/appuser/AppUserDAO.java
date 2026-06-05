package com.hammi.q_bus_backend.supabase.appuser;

import jakarta.validation.constraints.NotBlank;

public record AppUserDAO(
        @NotBlank(message = "First name is required")
        String firstName,
        @NotBlank(message = "Last name is required")
        String lastName,
        @NotBlank(message = "Phone number is required")
        String phoneNumber,
        String profileUrl
) {
}
