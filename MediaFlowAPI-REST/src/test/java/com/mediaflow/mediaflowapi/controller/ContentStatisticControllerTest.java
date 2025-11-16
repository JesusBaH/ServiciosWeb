package com.mediaflow.mediaflowapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediaflow.mediaflowapi.dto.ContentStatisticRequest;
import com.mediaflow.mediaflowapi.dto.ContentStatisticResponse;
import com.mediaflow.mediaflowapi.service.ContentStatisticService;
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

import java.util.Objects;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ContentStatisticController.class)
class ContentStatisticControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ContentStatisticService contentStatisticService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "/api/v1/content-statistics";

    @TestConfiguration
    static class TestConfig {
        @Bean
        ContentStatisticService contentStatisticService() {
            return mock(ContentStatisticService.class);
        }
    }

    @BeforeEach
    void setUp() {
        reset(contentStatisticService);
    }

    @Test
    @DisplayName("POST / -> 201 Created")
    void createStatistic_ok() throws Exception {
        ContentStatisticRequest request = ContentStatisticRequest.builder()
                .contentId(101)
                .views(0)
                .likes(0)
                .dislikes(0)
                .commentsCount(0)
                .averageWatchSeconds(0.0)
                .build();

        ContentStatisticResponse response = ContentStatisticResponse.builder()
                .contentStatisticId(1)
                .contentId(101)
                .views(0)
                .likes(0)
                .dislikes(0)
                .commentsCount(0)
                .averageWatchSeconds(0.0)
                .build();

        when(contentStatisticService.createStatistic(any(ContentStatisticRequest.class))).thenReturn(response);

        mvc.perform(post(BASE_URL)
                        .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                        .content(Objects.requireNonNull(objectMapper.writeValueAsString(request))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.['content statistic identifier']").value(1))
                .andExpect(jsonPath("$.['content identifier']").value(101));
    }

    @Test
    @DisplayName("POST / with invalid data -> 400 Bad Request")
    void createStatistic_invalidRequest() throws Exception {
        // Request inválido: contentId nulo y vistas negativas
        ContentStatisticRequest request = ContentStatisticRequest.builder()
                .contentId(null) 
                .views(-5)
                .build();

        mvc.perform(post(BASE_URL)
                        .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                        .content(Objects.requireNonNull(objectMapper.writeValueAsString(request))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", Objects.requireNonNull(containsStringIgnoringCase("Validation failed"))));
    }

    @Test
    @DisplayName("GET /{contentId} -> 200 OK")
    void getStatisticByContentId_ok() throws Exception {
        ContentStatisticResponse response = ContentStatisticResponse.builder()
                .contentStatisticId(1)
                .contentId(101)
                .views(100)
                .build();

        when(contentStatisticService.getStatisticByContentId(101)).thenReturn(response);

        mvc.perform(get(BASE_URL + "/{contentId}", 101))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['content statistic identifier']").value(1))
                .andExpect(jsonPath("$.['views count']").value(100));
    }

    @Test
    @DisplayName("PUT /{contentId} -> 200 OK")
    void updateStatistic_ok() throws Exception {
        ContentStatisticRequest request = ContentStatisticRequest.builder()
                .contentId(101)
                .views(50)
                .build();

        ContentStatisticResponse response = ContentStatisticResponse.builder()
                .contentStatisticId(1)
                .contentId(101)
                .views(50)
                .build();

        when(contentStatisticService.updateStatistic(eq(101), any(ContentStatisticRequest.class))).thenReturn(response);

        mvc.perform(put(BASE_URL + "/{contentId}", 101)
                        .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                        .content(Objects.requireNonNull(objectMapper.writeValueAsString(request))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['views count']").value(50));
    }

    @Test
    @DisplayName("DELETE /{contentId} existing -> 204 No Content")
    void deleteStatistic_ok() throws Exception {
        doNothing().when(contentStatisticService).deleteStatistic(101);

        mvc.perform(delete(BASE_URL + "/{contentId}", 101))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /{contentId} not existing -> 404 Not Found")
    void deleteStatistic_notFound() throws Exception {
        doThrow(new EntityNotFoundException("Statistics not found")).when(contentStatisticService).deleteStatistic(999);

        mvc.perform(delete(BASE_URL + "/{contentId}", 999))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Statistics not found"));
    }
}