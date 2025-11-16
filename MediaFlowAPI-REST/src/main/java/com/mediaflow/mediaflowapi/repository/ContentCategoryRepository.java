package com.mediaflow.mediaflowapi.repository;

import com.mediaflow.mediaflowapi.model.ContentCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContentCategoryRepository extends JpaRepository<ContentCategory, Integer> {

    Optional<ContentCategory> findByContent_ContentIdAndCategory_CategoryId(Integer contentId, Integer categoryId);

    List<ContentCategory> findByContent_ContentIdOrderByScoreDesc(Integer contentId);

    List<ContentCategory> findByCategory_CategoryId(Integer categoryId, Pageable pageable);

    @Transactional
    void deleteByContent_ContentIdAndCategory_CategoryId(Integer contentId, Integer categoryId);
}