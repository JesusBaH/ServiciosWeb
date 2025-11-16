package com.mediaflow.MediaFlowAPI_GraphQL.service;

import com.mediaflow.MediaFlowAPI_GraphQL.dto.PlaybackHistoryRequest;
import com.mediaflow.MediaFlowAPI_GraphQL.dto.PlaybackHistoryGraphqlResponse;
import java.util.List;

public interface PlaybackHistoryService {

    PlaybackHistoryGraphqlResponse createHistoryRecord(PlaybackHistoryRequest request);
    
    List<PlaybackHistoryGraphqlResponse> getUserHistory(Long userId, int page, int pageSize);

    void deleteHistoryRecord(Long historyId);

    void clearUserHistory(Long userId);
}