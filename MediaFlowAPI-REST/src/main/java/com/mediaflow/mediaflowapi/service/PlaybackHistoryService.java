package com.mediaflow.mediaflowapi.service;

import com.mediaflow.mediaflowapi.dto.PlaybackHistoryRequest;
import com.mediaflow.mediaflowapi.dto.PlaybackHistoryResponse;
import java.util.List;

public interface PlaybackHistoryService {

    PlaybackHistoryResponse createHistoryRecord(PlaybackHistoryRequest request);
    
    List<PlaybackHistoryResponse> getUserHistory(Long userId, int page, int pageSize);

    void deleteHistoryRecord(Long historyId);

    void clearUserHistory(Long userId);
}