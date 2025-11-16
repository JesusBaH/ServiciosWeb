package com.mediaflow.mediaflowapi.service;

import com.mediaflow.mediaflowapi.dto.RatingRequest;
import com.mediaflow.mediaflowapi.dto.RatingResponse;
import java.util.List;

public interface RatingService {

    RatingResponse upsertRating(RatingRequest request);

    RatingResponse getUserRatingForContent(Long userId, Integer contentId);

    List<RatingResponse> getRatingsByContent(Integer contentId, int page, int pageSize);

    void deleteRating(Long userId, Integer contentId);
}