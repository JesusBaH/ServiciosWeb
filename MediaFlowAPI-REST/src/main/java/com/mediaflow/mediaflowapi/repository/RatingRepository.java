package com.mediaflow.mediaflowapi.repository;

import com.mediaflow.mediaflowapi.model.Rating;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    Optional<Rating> findByUser_UserIdAndContent_ContentId(Long userId, Integer contentId);

    List<Rating> findByContent_ContentId(Integer contentId, Pageable pageable);

    @Transactional
    void deleteByUser_UserIdAndContent_ContentId(Long userId, Integer contentId);
}