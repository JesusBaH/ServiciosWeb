package com.mediaflow.mediaflowapi.mapper;

import com.mediaflow.mediaflowapi.dto.ContentCategoryRequest;
import com.mediaflow.mediaflowapi.dto.ContentCategoryResponse;
import com.mediaflow.mediaflowapi.model.Category;
import com.mediaflow.mediaflowapi.model.Content;
import com.mediaflow.mediaflowapi.model.ContentCategory;

public final class ContentCategoryMapper {

    public static ContentCategoryResponse toResponse(ContentCategory entity) {
        if (entity == null) {
            return null;
        }
        return ContentCategoryResponse.builder()
                .contentCategoryId(entity.getContentCategoryId())
                .score(entity.getScore())
                .contentId(entity.getContent() != null ? entity.getContent().getContentId() : null)
                .categoryId(entity.getCategory() != null ? entity.getCategory().getCategoryId() : null)
                .build();
    }

    public static ContentCategory toEntity(ContentCategoryRequest dto) {
        if (dto == null) {
            return null;
        }

        Content contentStub = new Content();
        contentStub.setContentId(dto.getContentId());

        Category categoryStub = new Category();
        categoryStub.setCategoryId(dto.getCategoryId());

        return ContentCategory.builder()
                .content(contentStub)
                .category(categoryStub)
                .score(dto.getScore())
                .build();
    }
}