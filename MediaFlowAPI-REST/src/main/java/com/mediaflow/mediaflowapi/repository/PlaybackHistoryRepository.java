package com.mediaflow.mediaflowapi.repository;

import com.mediaflow.mediaflowapi.model.PlaybackHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PlaybackHistoryRepository extends JpaRepository<PlaybackHistory, Long> {

    List<PlaybackHistory> findByUser_UserIdOrderByViewedAtDesc(Long userId, Pageable pageable);

    @Transactional
    void deleteByUser_UserId(Long userId);
}