package com.mediaflow.mediaflowapi.service;

import com.mediaflow.mediaflowapi.dto.EmotionRequest;
import com.mediaflow.mediaflowapi.dto.EmotionResponse;
import java.util.List;

public interface EmotionService {
    EmotionResponse createEmotion(EmotionRequest request);
    
    List<EmotionResponse> getAllEmotions(int page, int pageSize); 
    
    EmotionResponse updateEmotion(Integer emotionId, EmotionRequest request);
    void deleteEmotion(Integer emotionId);
}