package com.mediaflow.mediaflowapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediaflow.mediaflowapi.dto.EmotionRequest;
import com.mediaflow.mediaflowapi.dto.EmotionResponse;
import com.mediaflow.mediaflowapi.service.ContentEmotionService;
import com.mediaflow.mediaflowapi.service.EmotionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EmotionController.class)
class EmotionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmotionService emotionService;

    @Autowired
    private ContentEmotionService contentEmotionService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "/api/v1/emotions";

    @TestConfiguration
    static class TestConfig {
        @Bean
        EmotionService emotionService() {
            return mock(EmotionService.class);
        }

        @Bean
        ContentEmotionService contentEmotionService() {
            return mock(ContentEmotionService.class);
        }
    }

    @BeforeEach
    void setUp() {
        reset(emotionService, contentEmotionService);
    }

    @Test
    @DisplayName("POST / -> 201 Created")
    void createEmotion_ok() throws Exception {
        EmotionRequest request = EmotionRequest.builder().name("Joy").build();
        EmotionResponse response = EmotionResponse.builder().emotionId(1).name("Joy").build();

        when(emotionService.createEmotion(any(EmotionRequest.class))).thenReturn(response);

        mvc.perform(post(BASE_URL)
                        .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                        .content(Objects.requireNonNull(objectMapper.writeValueAsString(request))))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("GET / -> 200 OK (Pagination)")
    void getAllEmotions_ok() throws Exception {
        EmotionResponse response = EmotionResponse.builder().emotionId(1).name("Joy").build();

        when(emotionService.getAllEmotions(0, 10)).thenReturn(List.of(response));

        mvc.perform(get(BASE_URL)
                        .queryParam("page", "0")
                        .queryParam("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Objects.requireNonNull(hasSize(1))));
    }
}