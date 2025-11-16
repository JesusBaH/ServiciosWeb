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
public class RatingRequest {

    @NotNull(message = "User ID cannot be null.")
    @JsonProperty("user identifier")
    private Long userId;

    @NotNull(message = "Content ID cannot be null.")
    @JsonProperty("content identifier")
    private Integer contentId;

    @NotNull(message = "Score cannot be null.")
    @Min(value = 1, message = "Score must be at least 1.")
    @Max(value = 5, message = "Score must be at most 5.")
    @JsonProperty("rating score")
    private Integer score;
}