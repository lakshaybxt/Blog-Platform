package com.lakshay.blog_be.controller;

import com.lakshay.blog_be.domain.dtos.CategoryDto;
import com.lakshay.blog_be.domain.dtos.CreateCategoryRequest;
import com.lakshay.blog_be.domain.dtos.UpdateCategoryRequest;
import com.lakshay.blog_be.domain.entities.Category;
import com.lakshay.blog_be.domain.entities.User;
import com.lakshay.blog_be.mappers.CategoryMapper;
import com.lakshay.blog_be.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> listCategories() {
        List<Category> categories = categoryService.listCategories();
        return ResponseEntity.ok(
                categories.stream()
                        .map(category -> categoryMapper.toDto(category))
                        .toList()
        );
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(
            @Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
        Category category = categoryMapper.toEntity(createCategoryRequest);
        Category savedCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(
                categoryMapper.toDto(savedCategory),
                HttpStatus.CREATED
        );
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCategoryRequest updateCategoryRequest
    ) {
        Category updatedCategory = categoryService.updateCategory(id, updateCategoryRequest);
        return ResponseEntity.ok(categoryMapper.toDto(updatedCategory));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteCategory (@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
