package com.mediaflow.mediaflowapi.mapper;

import com.mediaflow.mediaflowapi.dto.EmotionRequest;
import com.mediaflow.mediaflowapi.dto.EmotionResponse;
import com.mediaflow.mediaflowapi.model.Emotion;

public final class EmotionMapper {

    public static EmotionResponse toResponse(Emotion entity) {
        if (entity == null) {
            return null;
        }
        return EmotionResponse.builder()
                .emotionId(entity.getEmotionId())
                .name(entity.getName())
                .build();
    }

    public static Emotion toEntity(EmotionRequest dto) {
        if (dto == null) {
            return null;
        }
        return Emotion.builder()
                .name(dto.getName())
                .build();
    }

    public static void updateEntity(EmotionRequest dto, Emotion entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setName(dto.getName());
    }
}