package com.mediaflow.mediaflowapi.controller;

import com.mediaflow.mediaflowapi.dto.ContentStatisticRequest;
import com.mediaflow.mediaflowapi.dto.ContentStatisticResponse;
import com.mediaflow.mediaflowapi.service.ContentStatisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/content-statistics")
@RequiredArgsConstructor
@Tag(name = "Content Statistics", description = "Endpoints for managing content performance metrics")
public class ContentStatisticController {

    private final ContentStatisticService contentStatisticService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create statistics for a content")
    @ApiResponse(responseCode = "201", description = "Statistic created successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid request data.")
    public ContentStatisticResponse createStatistic(
            @Valid @RequestBody ContentStatisticRequest request) {
        return contentStatisticService.createStatistic(request);
    }

    @GetMapping("/{contentId}")
    @Operation(summary = "Get statistics by content ID")
    @ApiResponse(responseCode = "200", description = "Content statistics found.")
    @ApiResponse(responseCode = "404", description = "Statistics not found for the given content.")
    public ContentStatisticResponse getStatisticByContentId(@PathVariable Integer contentId) {
        return contentStatisticService.getStatisticByContentId(contentId);
    }

    @PutMapping("/{contentId}")
    @Operation(summary = "Update statistics for a content")
    @ApiResponse(responseCode = "200", description = "Statistic updated successfully.")
    @ApiResponse(responseCode = "404", description = "Statistics not found.")
    public ContentStatisticResponse updateStatistic(
            @PathVariable Integer contentId,
            @Valid @RequestBody ContentStatisticRequest request) {
        return contentStatisticService.updateStatistic(contentId, request);
    }

    @DeleteMapping("/{contentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete statistics for a content")
    @ApiResponse(responseCode = "204", description = "Statistic deleted successfully.")
    @ApiResponse(responseCode = "404", description = "Statistics not found.")
    public void deleteStatistic(@PathVariable Integer contentId) {
        contentStatisticService.deleteStatistic(contentId);
    }
}