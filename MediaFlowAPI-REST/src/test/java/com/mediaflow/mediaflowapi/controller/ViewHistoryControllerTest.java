package com.mediaflow.mediaflowapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediaflow.mediaflowapi.dto.PlaybackHistoryRequest;
import com.mediaflow.mediaflowapi.dto.PlaybackHistoryResponse;
import com.mediaflow.mediaflowapi.service.PlaybackHistoryService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PlaybackHistoryController.class)
class PlaybackHistoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PlaybackHistoryService playbackHistoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "/api/v1/history";

    @TestConfiguration
    static class TestConfig {
        @Bean
        PlaybackHistoryService playbackHistoryService() {
            return mock(PlaybackHistoryService.class);
        }
    }

    @BeforeEach
    void setUp() {
        reset(playbackHistoryService);
    }

    @Test
    @DisplayName("POST / -> 201 Created")
    void createHistoryRecord_ok() throws Exception {
        PlaybackHistoryRequest request = new PlaybackHistoryRequest();
        request.setUserId(1L);
        request.setContentId(101);
        request.setWatchedSeconds(120);
        request.setUserAgent("Test-Agent");

        PlaybackHistoryResponse response = PlaybackHistoryResponse.builder()
                .playbackHistoryId(1L)
                .userId(request.getUserId())
                .contentId(request.getContentId())
                .watchedSeconds(request.getWatchedSeconds())
                .userAgent(request.getUserAgent())
                .viewedAt(LocalDateTime.now())
                .build();

        when(playbackHistoryService.createHistoryRecord(any(PlaybackHistoryRequest.class))).thenReturn(response);

        mvc.perform(post(BASE_URL)
                        .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                        .content(Objects.requireNonNull(objectMapper.writeValueAsString(request))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.['history identifier']").value(1L))
                .andExpect(jsonPath("$.['user agent']").value("Test-Agent"));
    }

    @Test
    @DisplayName("POST / with invalid data -> 400 Bad Request")
    void createHistoryRecord_invalidRequest() throws Exception {
        PlaybackHistoryRequest request = new PlaybackHistoryRequest();
        request.setUserId(null);
        request.setContentId(101);
        request.setWatchedSeconds(120);
        request.setUserAgent("Test-Agent");

        mvc.perform(post(BASE_URL)
                        .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                        .content(Objects.requireNonNull(objectMapper.writeValueAsString(request))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", Objects.requireNonNull(containsStringIgnoringCase("Validation failed"))));
    }

    @ParameterizedTest(name = "GET /user/{0}/pagination?page={1}&pageSize={2} -> 200 OK")
    @CsvSource({
            "5, 0, 10",
            "5, 1, 5"
    })
    @DisplayName("GET /user/{userId}/pagination with valid params -> 200 OK")
    void getUserHistory_ok(Long userId, int page, int pageSize) throws Exception {
        PlaybackHistoryResponse response = PlaybackHistoryResponse.builder()
                .playbackHistoryId(1L)
                .userId(userId)
                .viewedAt(LocalDateTime.now())
                .build();
        when(playbackHistoryService.getUserHistory(eq(userId), eq(page), eq(pageSize)))
                .thenReturn(List.of(response));

        mvc.perform(get(BASE_URL + "/user/{userId}/pagination", userId)
                        .queryParam("page", String.valueOf(page))
                        .queryParam("pageSize", String.valueOf(pageSize)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Objects.requireNonNull(hasSize(1))))
                .andExpect(jsonPath("$[0].['history identifier']").value(1L));
    }

    @Test
    @DisplayName("GET /user/{userId}/pagination with no results -> 200 OK with empty list")
    void getUserHistory_empty() throws Exception {
        when(playbackHistoryService.getUserHistory(eq(99L), eq(0), eq(10)))
                .thenReturn(Collections.emptyList());

        mvc.perform(get(BASE_URL + "/user/{userId}/pagination", 99L)
                        .queryParam("page", "0")
                        .queryParam("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Objects.requireNonNull(hasSize(0))));
    }

    @Test
    @DisplayName("GET /user/{userId}/pagination with invalid params -> 400 Bad Request")
    void getUserHistory_invalidParams() throws Exception {
        mvc.perform(get(BASE_URL + "/user/{userId}/pagination", 5L)
                        .queryParam("page", "-1")
                        .queryParam("pageSize", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", Objects.requireNonNull(containsStringIgnoringCase("Invalid pagination parameters"))));
    }

    @Test
    @DisplayName("DELETE /{historyId} existing -> 204 No Content")
    void deleteHistoryRecord_ok() throws Exception {
        doNothing().when(playbackHistoryService).deleteHistoryRecord(1L);

        mvc.perform(delete(BASE_URL + "/{historyId}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /{historyId} not existing -> 404 Not Found")
    void deleteHistoryRecord_notFound() throws Exception {
        doThrow(new EntityNotFoundException("Record not found")).when(playbackHistoryService).deleteHistoryRecord(99L);

        mvc.perform(delete(BASE_URL + "/{historyId}", 99L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Record not found"));
    }

    @Test
    @DisplayName("DELETE /user/{userId} -> 204 No Content")
    void clearUserHistory_ok() throws Exception {
        doNothing().when(playbackHistoryService).clearUserHistory(5L);

        mvc.perform(delete(BASE_URL + "/user/{userId}", 5L))
                .andExpect(status().isNoContent());
    }
}