package com.hammi.q_bus_backend.modules.providers;

import com.hammi.q_bus_backend.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/providers")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService providerService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Provider>>> getProviders() {
        return ResponseEntity.ok(new ApiResponse<>(providerService.getAllProviders()));
    }
}
