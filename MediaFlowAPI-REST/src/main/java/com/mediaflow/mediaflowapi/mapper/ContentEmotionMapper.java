package com.mediaflow.mediaflowapi.mapper;

import com.mediaflow.mediaflowapi.dto.ContentEmotionRequest;
import com.mediaflow.mediaflowapi.dto.ContentEmotionResponse;
import com.mediaflow.mediaflowapi.model.Content;
import com.mediaflow.mediaflowapi.model.ContentEmotion;
import com.mediaflow.mediaflowapi.model.Emotion;

public final class ContentEmotionMapper {

    public static ContentEmotionResponse toResponse(ContentEmotion entity) {
        if (entity == null) {
            return null;
        }
        return ContentEmotionResponse.builder()
                .contentEmotionId(entity.getContentEmotionId())
                .score(entity.getScore())
                .contentId(entity.getContent() != null ? entity.getContent().getContentId() : null)
                .emotionId(entity.getEmotion() != null ? entity.getEmotion().getEmotionId() : null)
                .build();
    }

    public static ContentEmotion toEntity(ContentEmotionRequest dto) {
        if (dto == null) {
            return null;
        }

        Content contentStub = new Content();
        contentStub.setContentId(dto.getContentId());

        Emotion emotionStub = new Emotion();
        emotionStub.setEmotionId(dto.getEmotionId());

        return ContentEmotion.builder()
                .content(contentStub)
                .emotion(emotionStub)
                .score(dto.getScore())
                .build();
    }
}