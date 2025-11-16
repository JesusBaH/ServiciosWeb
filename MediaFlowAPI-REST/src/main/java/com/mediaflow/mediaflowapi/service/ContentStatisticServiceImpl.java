package com.mediaflow.mediaflowapi.service;

import com.mediaflow.mediaflowapi.dto.ContentStatisticRequest;
import com.mediaflow.mediaflowapi.dto.ContentStatisticResponse;
import com.mediaflow.mediaflowapi.mapper.ContentStatisticMapper;
import com.mediaflow.mediaflowapi.model.ContentStatistic;
import com.mediaflow.mediaflowapi.repository.ContentStatisticRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ContentStatisticServiceImpl implements ContentStatisticService {

    private final ContentStatisticRepository contentStatisticRepository;

    @Override
    @NonNull
    @Transactional
    public ContentStatisticResponse createStatistic(ContentStatisticRequest request) {
        ContentStatistic newStatistic = Objects.requireNonNull(ContentStatisticMapper.toEntity(request));
        ContentStatistic savedStatistic = contentStatisticRepository.save(newStatistic);
        return Objects.requireNonNull(ContentStatisticMapper.toResponse(savedStatistic));
    }

    @Override
    @Transactional(readOnly = true)
    public ContentStatisticResponse getStatisticByContentId(Integer contentId) {
        Objects.requireNonNull(contentId);
        return contentStatisticRepository.findByContent_ContentId(contentId)
                .map(ContentStatisticMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("ContentStatistic not found for content id: " + contentId));
    }

    @Override
    @NonNull
    @Transactional
    public ContentStatisticResponse updateStatistic(Integer contentId, ContentStatisticRequest request) {
        Objects.requireNonNull(contentId);
        
        ContentStatistic existingStatistic = Objects.requireNonNull(
            contentStatisticRepository.findByContent_ContentId(contentId)
                .orElseThrow(() -> new EntityNotFoundException("ContentStatistic not found for content id: " + contentId))
        );
        
        ContentStatisticMapper.copyToEntity(request, existingStatistic);
        ContentStatistic savedStatistic = contentStatisticRepository.save(existingStatistic);
        return Objects.requireNonNull(ContentStatisticMapper.toResponse(savedStatistic));
    }

    @Override
    @Transactional
    public void deleteStatistic(Integer contentId) {
        Objects.requireNonNull(contentId);
        if (!contentStatisticRepository.findByContent_ContentId(contentId).isPresent()) {
            throw new EntityNotFoundException("ContentStatistic not found for content id: " + contentId);
        }
        contentStatisticRepository.deleteByContent_ContentId(contentId);
    }
}