package com.lakshay.blog_be.service.impl;

import com.lakshay.blog_be.domain.dtos.UpdateCategoryRequest;
import com.lakshay.blog_be.domain.entities.Category;
import com.lakshay.blog_be.repositories.CategoryRepository;
import com.lakshay.blog_be.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAllWithPostCount();
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        if(categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new IllegalArgumentException("Category already exists with name: " + category.getName());
        }
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteCategory(UUID id) {
        Category category = getCategoryById(id);

        if(!category.getPosts().isEmpty()) {
            throw new IllegalStateException("Cannot delete Category: " + category.getName() + ". It has associated posts.");
        }
        categoryRepository.delete(category);
    }

    @Override
    public Category getCategoryById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " +id));
    }

    @Override
    @Transactional
    public Category updateCategory(UUID id, UpdateCategoryRequest updateCategoryRequest) {
        Category existingCategory = getCategoryById(id);

        if(!existingCategory.getName().equalsIgnoreCase(updateCategoryRequest.getName())) {
            if(categoryRepository.existsByNameIgnoreCase(updateCategoryRequest.getName())) {
                throw new IllegalArgumentException("Category already exists with name: " + updateCategoryRequest.getName());
            }
            existingCategory.setName(updateCategoryRequest.getName());
        }

        return categoryRepository.save(existingCategory);
    }
}
