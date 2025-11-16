package com.mediaflow.MediaFlowAPI_GraphQL.service;

import com.mediaflow.MediaFlowAPI_GraphQL.model.Content;
import com.mediaflow.MediaFlowAPI_GraphQL.repository.ContentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;

    @Override
    @Transactional(readOnly = true)
    public Content findById(Integer id) {
        Objects.requireNonNull(id);
        return contentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Content not found with id: " + id));
    }
}