package com.lakshay.blog_be.service;

import com.lakshay.blog_be.domain.dto.UpdateCategoryRequest;
import com.lakshay.blog_be.domain.entities.Category;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    Category getCategoryById(UUID id);
    List<Category> listCategories();
    Category createCategory(Category category);
    Category updateCategory(UUID id, @Valid UpdateCategoryRequest updateCategoryRequest);
    void deleteCategory(UUID id);
}
