package com.mediaflow.mediaflowapi.mapper;

import com.mediaflow.mediaflowapi.dto.ContentStatisticRequest;
import com.mediaflow.mediaflowapi.dto.ContentStatisticResponse;
import com.mediaflow.mediaflowapi.model.Content;
import com.mediaflow.mediaflowapi.model.ContentStatistic;

public final class ContentStatisticMapper {

    public static ContentStatisticResponse toResponse(ContentStatistic entity) {
        if (entity == null) {
            return null;
        }
        return ContentStatisticResponse.builder()
                .contentStatisticId(entity.getContentStatisticId())
                .views(entity.getViews())
                .likes(entity.getLikes())
                .dislikes(entity.getDislikes())
                .commentsCount(entity.getCommentsCount())
                .averageWatchSeconds(entity.getAverageWatchSeconds())
                .contentId(entity.getContent() != null ? entity.getContent().getContentId() : null)
                .build();
    }

    public static ContentStatistic toEntity(ContentStatisticRequest dto) {
        if (dto == null) {
            return null;
        }

        Content contentStub = new Content();
        contentStub.setContentId(dto.getContentId()); 

        return ContentStatistic.builder()
                .content(contentStub)
                .views(dto.getViews())
                .likes(dto.getLikes())
                .dislikes(dto.getDislikes())
                .commentsCount(dto.getCommentsCount())
                .averageWatchSeconds(dto.getAverageWatchSeconds())
                .build();
    }

    public static void copyToEntity(ContentStatisticRequest dto, ContentStatistic entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setViews(dto.getViews());
        entity.setLikes(dto.getLikes());
        entity.setDislikes(dto.getDislikes());
        entity.setCommentsCount(dto.getCommentsCount());
        entity.setAverageWatchSeconds(dto.getAverageWatchSeconds());
    }
}