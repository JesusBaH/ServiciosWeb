package com.mediaflow.MediaFlowAPI_GraphQL.service;

import com.mediaflow.MediaFlowAPI_GraphQL.model.Content;

public interface ContentService {
    Content findById(Integer id);
}