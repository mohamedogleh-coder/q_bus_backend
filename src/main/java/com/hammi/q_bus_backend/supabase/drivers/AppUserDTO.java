package com.hammi.q_bus_backend.supabase.drivers;

import java.util.List;

public record AppUserDTO(
        String firstName,
        String lastName,
        String phoneNumber,
        String profileUrl,
        List<String> roleNames
) {
}
