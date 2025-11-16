package com.mediaflow.mediaflowapi.controller;

import com.mediaflow.mediaflowapi.dto.CategoryRequest;
import com.mediaflow.mediaflowapi.dto.CategoryResponse;
import com.mediaflow.mediaflowapi.dto.ContentCategoryRequest;
import com.mediaflow.mediaflowapi.dto.ContentCategoryResponse;
import com.mediaflow.mediaflowapi.service.CategoryService;
import com.mediaflow.mediaflowapi.service.ContentCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Categories & Classification", description = "Manage categories and content classification")
public class CategoryController {

    private final CategoryService categoryService;
    private final ContentCategoryService contentCategoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new category")
    public CategoryResponse createCategory(@Valid @RequestBody CategoryRequest request) {
        return categoryService.createCategory(request);
    }

    @GetMapping
    @Operation(summary = "Get all categories with pagination")
    public List<CategoryResponse> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        
        if (page < 0 || pageSize < 0) {
            throw new IllegalArgumentException("Pagination parameters cannot be negative.");
        }
        return categoryService.getAllCategories(page, pageSize);
    }

    @PutMapping("/{categoryId}")
    @Operation(summary = "Update a category")
    public CategoryResponse updateCategory(@PathVariable Integer categoryId, @Valid @RequestBody CategoryRequest request) {
        return categoryService.updateCategory(categoryId, request);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a category")
    public void deleteCategory(@PathVariable Integer categoryId) {
        categoryService.deleteCategory(categoryId);
    }

    @PostMapping("/assign")
    @Operation(summary = "Assign a category to content (with score)")
    public ContentCategoryResponse assignCategory(@Valid @RequestBody ContentCategoryRequest request) {
        return contentCategoryService.assignCategoryToContent(request);
    }

    @GetMapping("/content/{contentId}")
    @Operation(summary = "Get categories for a specific content")
    public List<ContentCategoryResponse> getCategoriesByContent(@PathVariable Integer contentId) {
        return contentCategoryService.getCategoriesByContent(contentId);
    }

    @DeleteMapping("/content/{contentId}/category/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove a category from content")
    public void removeCategory(@PathVariable Integer contentId, @PathVariable Integer categoryId) {
        contentCategoryService.removeCategoryFromContent(contentId, categoryId);
    }
}