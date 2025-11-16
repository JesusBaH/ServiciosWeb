package com.mediaflow.mediaflowapi.controller;

import com.mediaflow.mediaflowapi.dto.ContentEmotionRequest;
import com.mediaflow.mediaflowapi.dto.ContentEmotionResponse;
import com.mediaflow.mediaflowapi.dto.EmotionRequest;
import com.mediaflow.mediaflowapi.dto.EmotionResponse;
import com.mediaflow.mediaflowapi.service.ContentEmotionService;
import com.mediaflow.mediaflowapi.service.EmotionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/emotions")
@RequiredArgsConstructor
@Tag(name = "Emotions & Analysis", description = "Manage emotions and content analysis")
public class EmotionController {

    private final EmotionService emotionService;
    private final ContentEmotionService contentEmotionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new emotion")
    public EmotionResponse createEmotion(@Valid @RequestBody EmotionRequest request) {
        return emotionService.createEmotion(request);
    }

    @GetMapping
    @Operation(summary = "Get all emotions with pagination")
    public List<EmotionResponse> getAllEmotions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        
        if (page < 0 || pageSize < 0) {
             throw new IllegalArgumentException("Pagination parameters cannot be negative.");
        }
        return emotionService.getAllEmotions(page, pageSize);
    }

    @PutMapping("/{emotionId}")
    @Operation(summary = "Update an emotion")
    public EmotionResponse updateEmotion(@PathVariable Integer emotionId, @Valid @RequestBody EmotionRequest request) {
        return emotionService.updateEmotion(emotionId, request);
    }

    @DeleteMapping("/{emotionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an emotion")
    public void deleteEmotion(@PathVariable Integer emotionId) {
        emotionService.deleteEmotion(emotionId);
    }

    @PostMapping("/assign")
    @Operation(summary = "Assign an emotion to content (with score)")
    public ContentEmotionResponse assignEmotion(@Valid @RequestBody ContentEmotionRequest request) {
        return contentEmotionService.assignEmotionToContent(request);
    }

    @GetMapping("/content/{contentId}")
    @Operation(summary = "Get emotions for a specific content")
    public List<ContentEmotionResponse> getEmotionsByContent(@PathVariable Integer contentId) {
        return contentEmotionService.getEmotionsByContent(contentId);
    }

    @DeleteMapping("/content/{contentId}/emotion/{emotionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove an emotion from content")
    public void removeEmotion(@PathVariable Integer contentId, @PathVariable Integer emotionId) {
        contentEmotionService.removeEmotionFromContent(contentId, emotionId);
    }
}