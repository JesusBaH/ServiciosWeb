package com.mediaflow.mediaflowapi.service;

import com.mediaflow.mediaflowapi.dto.ContentEmotionRequest;
import com.mediaflow.mediaflowapi.dto.ContentEmotionResponse;
import java.util.List;

public interface ContentEmotionService {
    ContentEmotionResponse assignEmotionToContent(ContentEmotionRequest request);
    List<ContentEmotionResponse> getEmotionsByContent(Integer contentId);
    void removeEmotionFromContent(Integer contentId, Integer emotionId);
}