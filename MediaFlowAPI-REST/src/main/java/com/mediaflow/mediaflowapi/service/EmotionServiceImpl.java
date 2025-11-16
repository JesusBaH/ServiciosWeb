package com.mediaflow.mediaflowapi.service;

import com.mediaflow.mediaflowapi.dto.EmotionRequest;
import com.mediaflow.mediaflowapi.dto.EmotionResponse;
import com.mediaflow.mediaflowapi.mapper.EmotionMapper;
import com.mediaflow.mediaflowapi.model.Emotion;
import com.mediaflow.mediaflowapi.repository.EmotionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmotionServiceImpl implements EmotionService {

    private final EmotionRepository emotionRepository;

    @Override
    @NonNull
    @Transactional
    public EmotionResponse createEmotion(EmotionRequest request) {
        Objects.requireNonNull(request);
        if (emotionRepository.findByNameIgnoreCase(request.getName()).isPresent()) {
            throw new IllegalArgumentException("Emotion with name " + request.getName() + " already exists.");
        }
        Emotion newEmotion = Objects.requireNonNull(EmotionMapper.toEntity(request));
        return Objects.requireNonNull(EmotionMapper.toResponse(emotionRepository.save(newEmotion)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmotionResponse> getAllEmotions(int page, int pageSize) {
        PageRequest pageReq = PageRequest.of(page, pageSize);
        return emotionRepository.findAll(pageReq).stream()
                .map(EmotionMapper::toResponse)
                .toList();
    }

    @Override
    @NonNull
    @Transactional
    public EmotionResponse updateEmotion(Integer emotionId, EmotionRequest request) {
        Objects.requireNonNull(emotionId);
        Emotion emotion = Objects.requireNonNull(
            emotionRepository.findById(emotionId)
                .orElseThrow(() -> new EntityNotFoundException("Emotion not found with id: " + emotionId))
        );
        
        EmotionMapper.updateEntity(request, emotion);
        return Objects.requireNonNull(EmotionMapper.toResponse(emotionRepository.save(emotion)));
    }

    @Override
    @Transactional
    public void deleteEmotion(Integer emotionId) {
        Objects.requireNonNull(emotionId);
        if (!emotionRepository.existsById(emotionId)) {
            throw new EntityNotFoundException("Emotion not found with id: " + emotionId);
        }
        emotionRepository.deleteById(emotionId);
    }
}