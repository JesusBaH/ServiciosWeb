package com.mediaflow.MediaFlowAPI_GraphQL.mapper;

import com.mediaflow.MediaFlowAPI_GraphQL.dto.ContentResponse;
import com.mediaflow.MediaFlowAPI_GraphQL.model.Content;

public final class ContentMapper {

    public static ContentResponse toResponse(Content entity) {
        if (entity == null) {
            return null;
        }
        return ContentResponse.builder()
                .contentId(entity.getContentId())
                .title(entity.getTitle())
                .contentType(entity.getContentType())
                .description(entity.getDescription())
                .recommendedAge(entity.getRecommendedAge())
                .storageUrl(entity.getStorageUrl())
                .thumbnailUrl(entity.getThumbnailUrl())
                .created(entity.getCreated())
                .build();
    }
}