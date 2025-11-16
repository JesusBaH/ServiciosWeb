package com.mediaflow.mediaflowapi.service;

import com.mediaflow.mediaflowapi.dto.ContentEmotionRequest;
import com.mediaflow.mediaflowapi.dto.ContentEmotionResponse;
import com.mediaflow.mediaflowapi.mapper.ContentEmotionMapper;
import com.mediaflow.mediaflowapi.model.ContentEmotion;
import com.mediaflow.mediaflowapi.repository.ContentEmotionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContentEmotionServiceImpl implements ContentEmotionService {

    private final ContentEmotionRepository contentEmotionRepository;

    @Override
    @NonNull
    @Transactional
    public ContentEmotionResponse assignEmotionToContent(ContentEmotionRequest request) {
        Objects.requireNonNull(request);

        Optional<ContentEmotion> existingAssociation = contentEmotionRepository
                .findByContent_ContentIdAndEmotion_EmotionId(request.getContentId(), request.getEmotionId());

        ContentEmotion savedAssociation;
        if (existingAssociation.isPresent()) {
            ContentEmotion associationToUpdate = existingAssociation.get();
            associationToUpdate.setScore(request.getScore());
            savedAssociation = contentEmotionRepository.save(associationToUpdate);
        } else {
            ContentEmotion newAssociation = Objects.requireNonNull(ContentEmotionMapper.toEntity(request));
            savedAssociation = contentEmotionRepository.save(newAssociation);
        }
        
        return Objects.requireNonNull(ContentEmotionMapper.toResponse(savedAssociation));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContentEmotionResponse> getEmotionsByContent(Integer contentId) {
        Objects.requireNonNull(contentId);
        return contentEmotionRepository.findByContent_ContentIdOrderByScoreDesc(contentId).stream()
                .map(ContentEmotionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void removeEmotionFromContent(Integer contentId, Integer emotionId) {
        Objects.requireNonNull(contentId);
        Objects.requireNonNull(emotionId);
        
        if (!contentEmotionRepository.findByContent_ContentIdAndEmotion_EmotionId(contentId, emotionId).isPresent()) {
            throw new EntityNotFoundException("Association not found for content " + contentId + " and emotion " + emotionId);
        }
        contentEmotionRepository.deleteByContent_ContentIdAndEmotion_EmotionId(contentId, emotionId);
    }
}