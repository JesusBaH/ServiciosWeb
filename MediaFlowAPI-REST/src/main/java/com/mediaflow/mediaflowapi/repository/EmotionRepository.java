package com.mediaflow.mediaflowapi.repository;

import com.mediaflow.mediaflowapi.model.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmotionRepository extends JpaRepository<Emotion, Integer> {
    Optional<Emotion> findByNameIgnoreCase(String name);
}