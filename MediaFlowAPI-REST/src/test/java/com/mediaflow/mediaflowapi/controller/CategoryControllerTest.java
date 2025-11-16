package com.mediaflow.mediaflowapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediaflow.mediaflowapi.dto.CategoryRequest;
import com.mediaflow.mediaflowapi.dto.CategoryResponse;

import com.mediaflow.mediaflowapi.service.CategoryService;
import com.mediaflow.mediaflowapi.service.ContentCategoryService;

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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ContentCategoryService contentCategoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "/api/v1/categories";

    @TestConfiguration
    static class TestConfig {
        @Bean
        CategoryService categoryService() {
            return mock(CategoryService.class);
        }

        @Bean
        ContentCategoryService contentCategoryService() {
            return mock(ContentCategoryService.class);
        }
    }

    @BeforeEach
    void setUp() {
        reset(categoryService, contentCategoryService);
    }

    @Test
    @DisplayName("POST / -> 201 Created")
    void createCategory_ok() throws Exception {
        CategoryRequest request = CategoryRequest.builder().name("Action").build();
        CategoryResponse response = CategoryResponse.builder().categoryId(1).name("Action").build();

        when(categoryService.createCategory(any(CategoryRequest.class))).thenReturn(response);

        mvc.perform(post(BASE_URL)
                        .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                        .content(Objects.requireNonNull(objectMapper.writeValueAsString(request))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.['category identifier']").value(1));
    }

    @Test
    @DisplayName("GET / -> 200 OK (Pagination)")
    void getAllCategories_ok() throws Exception {
        CategoryResponse response = CategoryResponse.builder().categoryId(1).name("Action").build();

        // Simulamos respuesta paginada
        when(categoryService.getAllCategories(0, 10)).thenReturn(List.of(response));

        mvc.perform(get(BASE_URL)
                        .queryParam("page", "0")
                        .queryParam("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Objects.requireNonNull(hasSize(1))))
                .andExpect(jsonPath("$[0].name").value("Action"));
    }

    @Test
    @DisplayName("PUT /{categoryId} -> 200 OK")
    void updateCategory_ok() throws Exception {
        CategoryRequest request = CategoryRequest.builder().name("Drama").build();
        CategoryResponse response = CategoryResponse.builder().categoryId(1).name("Drama").build();

        when(categoryService.updateCategory(eq(1), any(CategoryRequest.class))).thenReturn(response);

        mvc.perform(put(BASE_URL + "/{categoryId}", 1)
                        .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                        .content(Objects.requireNonNull(objectMapper.writeValueAsString(request))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Drama"));
    }

    @Test
    @DisplayName("DELETE /{categoryId} -> 204 No Content")
    void deleteCategory_ok() throws Exception {
        doNothing().when(categoryService).deleteCategory(1);

        mvc.perform(delete(BASE_URL + "/{categoryId}", 1))
                .andExpect(status().isNoContent());
    }
}