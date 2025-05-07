package com.lakshay.blog_be.services.impl;

import com.lakshay.blog_be.domain.entities.Category;
import com.lakshay.blog_be.repository.CategoryRepository;
import com.lakshay.blog_be.services.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor //now we don't need to inject dependency by constructors
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAllWithPostCounts();
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        String categoryName = category.getName();
        if (categoryRepository.existsByNameIgnoreCase(categoryName)) {
            throw new IllegalArgumentException("Category already exists with name: " + categoryName);
        }
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(UUID id) {
        Optional<Category> category = categoryRepository.findById(id);

        if(category.isPresent()) {
            if(category.get().getPosts().size() > 0) {
                throw new IllegalArgumentException("Category has posts associated with it");
            }
            categoryRepository.deleteById(id);
        }
    }
}
