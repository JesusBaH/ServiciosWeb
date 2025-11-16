package com.mediaflow.mediaflowapi.service;

import com.mediaflow.mediaflowapi.dto.ContentCategoryRequest;
import com.mediaflow.mediaflowapi.dto.ContentCategoryResponse;
import java.util.List;

public interface ContentCategoryService {
    ContentCategoryResponse assignCategoryToContent(ContentCategoryRequest request);
    List<ContentCategoryResponse> getCategoriesByContent(Integer contentId);
    void removeCategoryFromContent(Integer contentId, Integer categoryId);
}