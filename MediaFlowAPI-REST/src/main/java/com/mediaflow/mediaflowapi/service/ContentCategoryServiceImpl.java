package com.mediaflow.mediaflowapi.service;

import com.mediaflow.mediaflowapi.dto.ContentCategoryRequest;
import com.mediaflow.mediaflowapi.dto.ContentCategoryResponse;
import com.mediaflow.mediaflowapi.mapper.ContentCategoryMapper;
import com.mediaflow.mediaflowapi.model.ContentCategory;
import com.mediaflow.mediaflowapi.repository.ContentCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContentCategoryServiceImpl implements ContentCategoryService {

    private final ContentCategoryRepository contentCategoryRepository;

    @Override
    @NonNull
    @Transactional
    public ContentCategoryResponse assignCategoryToContent(ContentCategoryRequest request) {
        Objects.requireNonNull(request);

        Optional<ContentCategory> existingAssociation = contentCategoryRepository
                .findByContent_ContentIdAndCategory_CategoryId(request.getContentId(), request.getCategoryId());

        ContentCategory savedAssociation;
        if (existingAssociation.isPresent()) {
            ContentCategory associationToUpdate = Objects.requireNonNull(existingAssociation.get());
            associationToUpdate.setScore(request.getScore());
            savedAssociation = contentCategoryRepository.save(associationToUpdate);
        } else {
            ContentCategory newAssociation = Objects.requireNonNull(ContentCategoryMapper.toEntity(request));
            savedAssociation = contentCategoryRepository.save(newAssociation);
        }
        
        return Objects.requireNonNull(ContentCategoryMapper.toResponse(savedAssociation));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContentCategoryResponse> getCategoriesByContent(Integer contentId) {
        Objects.requireNonNull(contentId);
        return contentCategoryRepository.findByContent_ContentIdOrderByScoreDesc(contentId).stream()
                .map(ContentCategoryMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void removeCategoryFromContent(Integer contentId, Integer categoryId) {
        Objects.requireNonNull(contentId);
        Objects.requireNonNull(categoryId);
        
        if (!contentCategoryRepository.findByContent_ContentIdAndCategory_CategoryId(contentId, categoryId).isPresent()) {
            throw new EntityNotFoundException("Association not found for content " + contentId + " and category " + categoryId);
        }
        contentCategoryRepository.deleteByContent_ContentIdAndCategory_CategoryId(contentId, categoryId);
    }
}