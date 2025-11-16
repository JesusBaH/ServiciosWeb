package com.mediaflow.mediaflowapi.mapper;

import com.mediaflow.mediaflowapi.dto.CategoryRequest;
import com.mediaflow.mediaflowapi.dto.CategoryResponse;
import com.mediaflow.mediaflowapi.model.Category;

public final class CategoryMapper {

    public static CategoryResponse toResponse(Category entity) {
        if (entity == null) {
            return null;
        }
        return CategoryResponse.builder()
                .categoryId(entity.getCategoryId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    public static Category toEntity(CategoryRequest dto) {
        if (dto == null) {
            return null;
        }
        return Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public static void updateEntity(CategoryRequest dto, Category entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
    }
}