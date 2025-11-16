package com.mediaflow.MediaFlowAPI_GraphQL.service;

import com.mediaflow.MediaFlowAPI_GraphQL.dto.PlaybackHistoryRequest;
import com.mediaflow.MediaFlowAPI_GraphQL.dto.PlaybackHistoryGraphqlResponse;
import com.mediaflow.MediaFlowAPI_GraphQL.mapper.PlaybackHistoryMapper;
import com.mediaflow.MediaFlowAPI_GraphQL.model.Content;
import com.mediaflow.MediaFlowAPI_GraphQL.model.PlaybackHistory;
import com.mediaflow.MediaFlowAPI_GraphQL.model.User;
import com.mediaflow.MediaFlowAPI_GraphQL.repository.ContentRepository;
import com.mediaflow.MediaFlowAPI_GraphQL.repository.PlaybackHistoryRepository;
import com.mediaflow.MediaFlowAPI_GraphQL.repository.UserRepository;

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
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;

    @Override
    @NonNull
    @Transactional
    public PlaybackHistoryGraphqlResponse createHistoryRecord(PlaybackHistoryRequest request) {
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + request.getUserId()));

        Content content = contentRepository.findById(request.getContentId())
            .orElseThrow(() -> new EntityNotFoundException("Content not found with id: " + request.getContentId()));
        
        PlaybackHistory newHistory = Objects.requireNonNull(PlaybackHistoryMapper.toEntity(request, user, content));
        
        PlaybackHistory savedHistory = playbackHistoryRepository.save(newHistory);
        return Objects.requireNonNull(PlaybackHistoryMapper.toGraphqlResponse(savedHistory));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlaybackHistoryGraphqlResponse> getUserHistory(Long userId, int page, int pageSize) {
        Objects.requireNonNull(userId);
        PageRequest pageReq = PageRequest.of(page, pageSize);
        return playbackHistoryRepository.findUserHistoryWithDetails(userId, pageReq).stream()
                .map(PlaybackHistoryMapper::toGraphqlResponse)
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