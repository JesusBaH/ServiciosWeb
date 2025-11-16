package com.mediaflow.mediaflowapi.service;

import com.mediaflow.mediaflowapi.dto.CategoryRequest;
import com.mediaflow.mediaflowapi.dto.CategoryResponse;
import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    List<CategoryResponse> getAllCategories(int page, int pageSize);
    CategoryResponse updateCategory(Integer categoryId, CategoryRequest request);
    void deleteCategory(Integer categoryId);
}