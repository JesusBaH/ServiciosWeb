package com.mediaflow.mediaflowapi.controller;

import com.mediaflow.mediaflowapi.dto.RatingRequest;
import com.mediaflow.mediaflowapi.dto.RatingResponse;
import com.mediaflow.mediaflowapi.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
@Tag(name = "Ratings", description = "Endpoints for managing user content ratings")
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK) 
    @Operation(summary = "Rate a content (Upsert)")
    @ApiResponse(responseCode = "200", description = "Rating created or updated successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid request data.")
    public RatingResponse upsertRating(@Valid @RequestBody RatingRequest request) {
        return ratingService.upsertRating(request);
    }

    @GetMapping("/user/{userId}/content/{contentId}")
    @Operation(summary = "Get a specific rating by user and content")
    @ApiResponse(responseCode = "200", description = "Rating found.")
    @ApiResponse(responseCode = "404", description = "Rating not found.")
    public RatingResponse getUserRating(
            @PathVariable Long userId,
            @PathVariable Integer contentId) {
        return ratingService.getUserRatingForContent(userId, contentId);
    }

    @GetMapping("/content/{contentId}")
    @Operation(summary = "Get all ratings for a content with pagination")
    public List<RatingResponse> getRatingsByContent(
            @PathVariable Integer contentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ratingService.getRatingsByContent(contentId, page, pageSize);
    }

    @DeleteMapping("/user/{userId}/content/{contentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove a rating")
    public void deleteRating(
            @PathVariable Long userId,
            @PathVariable Integer contentId) {
        ratingService.deleteRating(userId, contentId);
    }
}