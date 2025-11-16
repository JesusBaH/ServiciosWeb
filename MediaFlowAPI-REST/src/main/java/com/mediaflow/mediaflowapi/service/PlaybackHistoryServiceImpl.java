package com.mediaflow.mediaflowapi.service;

import com.mediaflow.mediaflowapi.dto.PlaybackHistoryRequest;
import com.mediaflow.mediaflowapi.dto.PlaybackHistoryResponse;
import com.mediaflow.mediaflowapi.mapper.PlaybackHistoryMapper;
import com.mediaflow.mediaflowapi.model.PlaybackHistory;
import com.mediaflow.mediaflowapi.repository.PlaybackHistoryRepository;
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
public class PlaybackHistoryServiceImpl implements PlaybackHistoryService {

    private final PlaybackHistoryRepository playbackHistoryRepository;

    @Override
    @NonNull
    @Transactional
    public PlaybackHistoryResponse createHistoryRecord(PlaybackHistoryRequest request) {
        PlaybackHistory newHistory = Objects.requireNonNull(PlaybackHistoryMapper.toEntity(request));
        PlaybackHistory savedHistory = playbackHistoryRepository.save(newHistory);
        return Objects.requireNonNull(PlaybackHistoryMapper.toResponse(savedHistory));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlaybackHistoryResponse> getUserHistory(Long userId, int page, int pageSize) {
        Objects.requireNonNull(userId);
        PageRequest pageReq = PageRequest.of(page, pageSize);
        return playbackHistoryRepository.findByUser_UserIdOrderByViewedAtDesc(userId, pageReq).stream()
                .map(PlaybackHistoryMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deleteHistoryRecord(Long historyId) {
        Objects.requireNonNull(historyId);
        if (!playbackHistoryRepository.existsById(historyId)) {
            throw new EntityNotFoundException("PlaybackHistory record not found with id: " + historyId);
        }
        playbackHistoryRepository.deleteById(historyId);
    }

    @Override
    @Transactional
    public void clearUserHistory(Long userId) {
        Objects.requireNonNull(userId);
        playbackHistoryRepository.deleteByUser_UserId(userId);
    }
}