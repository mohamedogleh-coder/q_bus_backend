package com.hammi.q_bus_backend.modules.categories;


import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

public record CategoryDAO(
        @NotBlank(message = "Category name required")
        @Size(max = 100, message = "Category name must not exceed 100 characters")
        String categoryName,

        @NotNull(message = "Number of seats is required")
        @Min(value = 14, message = "Number of seats must be greater than or equal to 14 seats")
        @Max(value = 35, message = "Number of seats must be less than or equal to 35 seats") // Fixed "14" to "35"
        Short numberOfSeats,

        @NotBlank(message = "Model year required")
        @Size(min = 4, max = 10, message = "Model year must be between 4 and 10 characters")
        String modelYear,

        MultipartFile image
) {
}
