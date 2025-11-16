package com.mediaflow.mediaflowapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediaflow.mediaflowapi.dto.RatingRequest;
import com.mediaflow.mediaflowapi.dto.RatingResponse;
import com.mediaflow.mediaflowapi.service.RatingService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
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

@WebMvcTest(controllers = RatingController.class)
class RatingControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "/api/v1/ratings";

    @TestConfiguration
    static class TestConfig {
        @Bean
        RatingService ratingService() {
            return mock(RatingService.class);
        }
    }

    @BeforeEach
    void setUp() {
        reset(ratingService);
    }

    @Test
    @DisplayName("POST / -> 200 OK (Upsert)")
    void upsertRating_ok() throws Exception {
        RatingRequest request = RatingRequest.builder()
                .userId(1L)
                .contentId(101)
                .score(5)
                .build();

        RatingResponse response = RatingResponse.builder()
                .ratingId(10)
                .userId(1L)
                .contentId(101)
                .score(5)
                .createdAt(LocalDateTime.now())
                .build();

        when(ratingService.upsertRating(any(RatingRequest.class))).thenReturn(response);

        mvc.perform(post(BASE_URL)
                        .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                        .content(Objects.requireNonNull(objectMapper.writeValueAsString(request))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['rating score']").value(5));
    }

    @Test
    @DisplayName("GET /user/{uid}/content/{cid} -> 200 OK")
    void getUserRating_ok() throws Exception {
        RatingResponse response = RatingResponse.builder()
                .ratingId(10)
                .score(4)
                .build();

        when(ratingService.getUserRatingForContent(1L, 101)).thenReturn(response);

        mvc.perform(get(BASE_URL + "/user/{userId}/content/{contentId}", 1L, 101))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['rating score']").value(4));
    }

    @Test
    @DisplayName("DELETE /user/{uid}/content/{cid} -> 204 No Content")
    void deleteRating_ok() throws Exception {
        doNothing().when(ratingService).deleteRating(1L, 101);

        mvc.perform(delete(BASE_URL + "/user/{userId}/content/{contentId}", 1L, 101))
                .andExpect(status().isNoContent());
    }
    
    @Test
    @DisplayName("DELETE /user/{uid}/content/{cid} -> 404 Not Found")
    void deleteRating_notFound() throws Exception {
        doThrow(new EntityNotFoundException("Rating not found"))
            .when(ratingService).deleteRating(99L, 99);

        mvc.perform(delete(BASE_URL + "/user/{userId}/content/{contentId}", 99L, 99))
                .andExpect(status().isNotFound());
    }
}