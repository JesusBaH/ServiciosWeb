package com.mediaflow.mediaflowapi.mapper;

import com.mediaflow.mediaflowapi.dto.PlaybackHistoryRequest;
import com.mediaflow.mediaflowapi.dto.PlaybackHistoryResponse;
import com.mediaflow.mediaflowapi.model.Content;
import com.mediaflow.mediaflowapi.model.PlaybackHistory;
import com.mediaflow.mediaflowapi.model.User;

import java.time.LocalDateTime;

public final class PlaybackHistoryMapper {

    public static PlaybackHistoryResponse toResponse(PlaybackHistory entity) {
        if (entity == null) {
            return null;
        }
        return PlaybackHistoryResponse.builder()
                .playbackHistoryId(entity.getPlaybackHistoryId())
                .watchedSeconds(entity.getWatchedSeconds())
                .userAgent(entity.getUserAgent())
                .viewedAt(entity.getViewedAt())
                .userId(entity.getUser() != null ? entity.getUser().getUserId() : null)
                .contentId(entity.getContent() != null ? entity.getContent().getContentId() : null)
                .build();
    }

    public static PlaybackHistory toEntity(PlaybackHistoryRequest dto) {
        if (dto == null) {
            return null;
        }

        User userStub = new User();
        userStub.setUserId(dto.getUserId()); 

        Content contentStub = new Content();
        contentStub.setContentId(dto.getContentId()); 
        
        return PlaybackHistory.builder()
                .user(userStub) 
                .content(contentStub) 
                .watchedSeconds(dto.getWatchedSeconds())
                .userAgent(dto.getUserAgent())
                .viewedAt(LocalDateTime.now()) 
                .build();
    }

    public static void copyToEntity(PlaybackHistoryRequest dto, PlaybackHistory entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setWatchedSeconds(dto.getWatchedSeconds());
        entity.setUserAgent(dto.getUserAgent());
        entity.setViewedAt(LocalDateTime.now());
    }
}