package com.mediaflow.mediaflowapi.mapper;

import com.mediaflow.mediaflowapi.dto.RatingRequest;
import com.mediaflow.mediaflowapi.dto.RatingResponse;
import com.mediaflow.mediaflowapi.model.Content;
import com.mediaflow.mediaflowapi.model.Rating;
import com.mediaflow.mediaflowapi.model.User;

import java.time.LocalDateTime;

public final class RatingMapper {

    public static RatingResponse toResponse(Rating entity) {
        if (entity == null) {
            return null;
        }
        return RatingResponse.builder()
                .ratingId(entity.getRatingId())
                .score(entity.getScore())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .userId(entity.getUser() != null ? entity.getUser().getUserId() : null)
                .contentId(entity.getContent() != null ? entity.getContent().getContentId() : null)
                .build();
    }

    public static Rating toEntity(RatingRequest dto) {
        if (dto == null) {
            return null;
        }

        User userStub = new User();
        userStub.setUserId(dto.getUserId());

        Content contentStub = new Content();
        contentStub.setContentId(dto.getContentId());

        return Rating.builder()
                .user(userStub)
                .content(contentStub)
                .score(dto.getScore())
                .createdAt(LocalDateTime.now()) 
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static void updateEntity(RatingRequest dto, Rating entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setScore(dto.getScore());
        entity.setUpdatedAt(LocalDateTime.now());
    }
}