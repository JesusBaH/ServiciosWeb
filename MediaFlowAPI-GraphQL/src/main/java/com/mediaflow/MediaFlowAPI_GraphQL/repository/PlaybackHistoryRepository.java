package com.mediaflow.MediaFlowAPI_GraphQL.repository;

import com.mediaflow.MediaFlowAPI_GraphQL.model.PlaybackHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PlaybackHistoryRepository extends JpaRepository<PlaybackHistory, Long> {

    @Query("SELECT ph FROM PlaybackHistory ph JOIN FETCH ph.user u JOIN FETCH ph.content c WHERE u.userId = :userId ORDER BY ph.viewedAt DESC")
    List<PlaybackHistory> findUserHistoryWithDetails(Long userId, Pageable pageable);

    @Transactional
    void deleteByUser_UserId(Long userId);
}