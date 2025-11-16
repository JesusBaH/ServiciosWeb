package com.mediaflow.MediaFlowAPI_GraphQL.mapper;

import com.mediaflow.MediaFlowAPI_GraphQL.dto.PlaybackHistoryRequest;
import com.mediaflow.MediaFlowAPI_GraphQL.dto.PlaybackHistoryGraphqlResponse;
import com.mediaflow.MediaFlowAPI_GraphQL.model.Content;
import com.mediaflow.MediaFlowAPI_GraphQL.model.PlaybackHistory;
import com.mediaflow.MediaFlowAPI_GraphQL.model.User;
import java.time.LocalDateTime;

public final class PlaybackHistoryMapper {

    public static PlaybackHistoryGraphqlResponse toGraphqlResponse(PlaybackHistory entity) {
        if (entity == null) {
            return null;
        }
        return PlaybackHistoryGraphqlResponse.builder()
                .playbackHistoryId(entity.getPlaybackHistoryId())
                .watchedSeconds(entity.getWatchedSeconds())
                .userAgent(entity.getUserAgent())
                .viewedAt(entity.getViewedAt())
                .user(UserMapper.toResponse(entity.getUser()))
                .content(ContentMapper.toResponse(entity.getContent()))
                .build();
    }

    public static PlaybackHistory toEntity(PlaybackHistoryRequest dto, User user, Content content) {
        if (dto == null || user == null || content == null) {
            return null;
        }

        return PlaybackHistory.builder()
                .user(user) 
                .content(content) 
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