package com.hammi.q_bus_backend.modules.categories;

import com.hammi.q_bus_backend.exceptions.ApiException;
import com.hammi.q_bus_backend.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private static final String UPLOAD_DIR = "src/main/resources/uploads/category";

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> {
                    String urlPath = (category.getImage() != null) ? getImagePath(category.getImage()) : null;
                    return new CategoryDTO(
                            category.getId(),
                            category.getCategoryName(),
                            category.getNumberOfSeats(),
                            category.getModelYear(),
                            urlPath
                    );
                })
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Short id) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        String urlPath = null;
        if (category.getImage() != null) {
            urlPath = getImagePath(category.getImage());
        }
        return new CategoryDTO(category.getId(), category.getCategoryName(), category.getNumberOfSeats(), category.getModelYear(), urlPath);
    }

    public CategoryDTO addCategory(CategoryDAO categoryDAO) {
        try {
            String imagePath = null;
            String urlPath = null;

            if (categoryDAO.image() != null && !categoryDAO.image().isEmpty()) {
                imagePath = saveCategoryImage(categoryDAO.image());
                urlPath = getImagePath(imagePath);
            }

            var category = Category.builder()
                    .categoryName(categoryDAO.categoryName())
                    .modelYear(categoryDAO.modelYear())
                    .numberOfSeats(categoryDAO.numberOfSeats())
                    .image(imagePath)
                    .build();

            var savedCategory = categoryRepository.save(category);

            return new CategoryDTO(savedCategory.getId(), savedCategory.getCategoryName(), savedCategory.getNumberOfSeats(), savedCategory.getModelYear(), urlPath);

        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("category_unq")) {
                throw new ApiException("This category already exists");
            } else {
                throw new ApiException(e.getMessage());
            }
        }
    }

    public String saveCategoryImage(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        String uniqueFileName = UUID.randomUUID() + fileExtension;

        Path filePath = uploadPath.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), filePath);

        return uniqueFileName;
    }

    private String getImagePath(String categoryUrl) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/categories/uploads/")
                .path(categoryUrl)
                .toUriString();
    }
}