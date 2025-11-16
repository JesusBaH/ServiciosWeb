package com.mediaflow.mediaflowapi.repository;

import com.mediaflow.mediaflowapi.model.ContentStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ContentStatisticRepository extends JpaRepository<ContentStatistic, Integer> {

    Optional<ContentStatistic> findByContent_ContentId(Integer contentId);

    @Transactional
    void deleteByContent_ContentId(Integer contentId);
}