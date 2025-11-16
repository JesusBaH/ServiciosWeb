package com.mediaflow.mediaflowapi.service;

import com.mediaflow.mediaflowapi.dto.RatingRequest;
import com.mediaflow.mediaflowapi.dto.RatingResponse;
import com.mediaflow.mediaflowapi.mapper.RatingMapper;
import com.mediaflow.mediaflowapi.model.Rating;
import com.mediaflow.mediaflowapi.repository.RatingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    @Override
    @NonNull
    @Transactional
    public RatingResponse upsertRating(RatingRequest request) {
        Objects.requireNonNull(request);
        
        Optional<Rating> existingRating = ratingRepository.findByUser_UserIdAndContent_ContentId(
                request.getUserId(), 
                request.getContentId()
        );

        Rating savedRating;
        if (existingRating.isPresent()) {
            Rating ratingToUpdate = Objects.requireNonNull(existingRating.get());
            RatingMapper.updateEntity(request, ratingToUpdate);
            savedRating = ratingRepository.save(ratingToUpdate);
        } else {
            Rating newRating = Objects.requireNonNull(RatingMapper.toEntity(request));
            savedRating = ratingRepository.save(newRating);
        }

        return Objects.requireNonNull(RatingMapper.toResponse(savedRating));
    }

    @Override
    @Transactional(readOnly = true)
    public RatingResponse getUserRatingForContent(Long userId, Integer contentId) {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(contentId);
        
        return ratingRepository.findByUser_UserIdAndContent_ContentId(userId, contentId)
                .map(RatingMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Rating not found for user " + userId + " and content " + contentId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RatingResponse> getRatingsByContent(Integer contentId, int page, int pageSize) {
        Objects.requireNonNull(contentId);
        PageRequest pageReq = PageRequest.of(page, pageSize);
        return ratingRepository.findByContent_ContentId(contentId, pageReq).stream()
                .map(RatingMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deleteRating(Long userId, Integer contentId) {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(contentId);
        
        if (!ratingRepository.findByUser_UserIdAndContent_ContentId(userId, contentId).isPresent()) {
             throw new EntityNotFoundException("Rating not found to delete");
        }
        ratingRepository.deleteByUser_UserIdAndContent_ContentId(userId, contentId);
    }
}