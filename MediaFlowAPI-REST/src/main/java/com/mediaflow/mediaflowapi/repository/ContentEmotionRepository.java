package com.mediaflow.mediaflowapi.repository;

import com.mediaflow.mediaflowapi.model.ContentEmotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContentEmotionRepository extends JpaRepository<ContentEmotion, Integer> {

    Optional<ContentEmotion> findByContent_ContentIdAndEmotion_EmotionId(Integer contentId, Integer emotionId);

    List<ContentEmotion> findByContent_ContentIdOrderByScoreDesc(Integer contentId);

    @Transactional
    void deleteByContent_ContentIdAndEmotion_EmotionId(Integer contentId, Integer emotionId);
}