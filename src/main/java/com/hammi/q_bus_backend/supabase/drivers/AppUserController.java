package com.hammi.q_bus_backend.supabase.drivers;

import com.hammi.q_bus_backend.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/drivers")
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<ApiResponse<AppUserDTO>> getDriverByPhoneNumber(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(new ApiResponse<>(appUserService.findByPhoneNumber(phoneNumber)));
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<AppUserDTO>> registerNewDriver(@Valid @RequestBody AppUserDAO request) {
        return ResponseEntity.ok(new ApiResponse<>(appUserService.registerNewDriver(request)));
    }
}
