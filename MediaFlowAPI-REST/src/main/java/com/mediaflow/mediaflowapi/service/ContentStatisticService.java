package com.mediaflow.mediaflowapi.service;

import com.mediaflow.mediaflowapi.dto.ContentStatisticRequest;
import com.mediaflow.mediaflowapi.dto.ContentStatisticResponse;

public interface ContentStatisticService {

    ContentStatisticResponse createStatistic(ContentStatisticRequest request);

    ContentStatisticResponse getStatisticByContentId(Integer contentId);

    ContentStatisticResponse updateStatistic(Integer contentId, ContentStatisticRequest request);

    void deleteStatistic(Integer contentId);
}