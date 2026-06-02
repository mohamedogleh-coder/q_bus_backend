package com.hammi.q_bus_backend.modules.categories;

import com.hammi.q_bus_backend.util.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private static final String UPLOAD_DIR = "src/main/resources/uploads/category";

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getAllCategories() {
        return ResponseEntity.ok(new ApiResponse<>(categoryService.getAllCategories()));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryDTO>> getCategoryById(@PathVariable Short categoryId) {
        return ResponseEntity.ok(new ApiResponse<>(categoryService.getCategoryById(categoryId)));
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<CategoryDTO>> addCategory(@Valid @ModelAttribute CategoryDAO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(categoryService.addCategory(request)));
    }


    @GetMapping("/uploads/{filename}")
    public ResponseEntity<Resource> showImage(@PathVariable String filename) {
        if (filename.contains("..") || filename.contains("/") || filename.contains("\\")) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Path filePath = Paths.get(UPLOAD_DIR).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                // Si toos ah u hel nooca sawirka (png, jpeg, giff...)
                String contentType = Files.probeContentType(filePath);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (MalformedURLException e) {
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
