package com.mediaflow.mediaflowapi.service;

import com.mediaflow.mediaflowapi.dto.CategoryRequest;
import com.mediaflow.mediaflowapi.dto.CategoryResponse;
import com.mediaflow.mediaflowapi.mapper.CategoryMapper;
import com.mediaflow.mediaflowapi.model.Category;
import com.mediaflow.mediaflowapi.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @NonNull
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        Objects.requireNonNull(request);
        if (categoryRepository.findByNameIgnoreCase(request.getName()).isPresent()) {
            throw new IllegalArgumentException("Category with name " + request.getName() + " already exists.");
        }
        Category newCategory = Objects.requireNonNull(CategoryMapper.toEntity(request));
        return Objects.requireNonNull(CategoryMapper.toResponse(categoryRepository.save(newCategory)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories(int page, int pageSize) {
        PageRequest pageReq = PageRequest.of(page, pageSize);
        return categoryRepository.findAll(pageReq).stream()
                .map(CategoryMapper::toResponse)
                .toList();
    }

    @Override
    @NonNull
    @Transactional
    public CategoryResponse updateCategory(Integer categoryId, CategoryRequest request) {
        Objects.requireNonNull(categoryId);
        Category category = Objects.requireNonNull(
            categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId))
        );
        
        CategoryMapper.updateEntity(request, category);
        return Objects.requireNonNull(CategoryMapper.toResponse(categoryRepository.save(category)));
    }

    @Override
    @Transactional
    public void deleteCategory(Integer categoryId) {
        Objects.requireNonNull(categoryId);
        if (!categoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException("Category not found with id: " + categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }
}