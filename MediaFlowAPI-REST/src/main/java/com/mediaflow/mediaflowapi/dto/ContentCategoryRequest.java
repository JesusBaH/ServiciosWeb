package com.mediaflow.mediaflowapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentCategoryRequest {

    @NotNull(message = "Content ID cannot be null.")
    @JsonProperty("content identifier")
    private Integer contentId;

    @NotNull(message = "Category ID cannot be null.")
    @JsonProperty("category identifier")
    private Integer categoryId;

    @NotNull(message = "Score cannot be null.")
    @Min(value = 0, message = "Score must be at least 0.0")
    @Max(value = 1, message = "Score must be at most 1.0")
    @JsonProperty("category score")
    private Double score;
}