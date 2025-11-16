package com.mediaflow.mediaflowapi.controller;

import com.mediaflow.mediaflowapi.dto.PlaybackHistoryRequest;
import com.mediaflow.mediaflowapi.dto.PlaybackHistoryResponse;
import com.mediaflow.mediaflowapi.service.PlaybackHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/history")
@RequiredArgsConstructor
@Tag(name = "Playback History", description = "Endpoints for managing user playback history")
public class PlaybackHistoryController {

    private final PlaybackHistoryService playbackHistoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new playback history record")
    @ApiResponse(responseCode = "201", description = "Record created successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid request data.")
    public PlaybackHistoryResponse createHistoryRecord(
            @Valid @RequestBody PlaybackHistoryRequest request) {
        return playbackHistoryService.createHistoryRecord(request);
    }

    @GetMapping(value = "/user/{userId}/pagination", params = { "page", "pageSize" })
    @Operation(summary = "Get playback history for a specific user with pagination")
    @ApiResponse(responseCode = "200", description = "List of user's playback history records.")
    public List<PlaybackHistoryResponse> getUserHistory(
            @PathVariable Long userId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

        if (page < 0 || pageSize < 0 || (page == 0 && pageSize == 0)) {
            throw new IllegalArgumentException(
                    "Invalid pagination parameters: page and pageSize cannot be negative and cannot both be 0.");
        }

        return playbackHistoryService.getUserHistory(userId, page, pageSize);
    }

    @DeleteMapping("/{historyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a specific history record")
    @ApiResponse(responseCode = "204", description = "Record deleted successfully.")
    @ApiResponse(responseCode = "404", description = "Record not found.")
    public void deleteHistoryRecord(@PathVariable Long historyId) {
        playbackHistoryService.deleteHistoryRecord(historyId);
    }

    @DeleteMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Clear all playback history for a user")
    @ApiResponse(responseCode = "204", description = "All records for the user were deleted successfully.")
    public void clearUserHistory(@PathVariable Long userId) {
        playbackHistoryService.clearUserHistory(userId);
    }
}