package com.mediaflow.mediaflowapi.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponse {

    @JsonProperty("rating identifier")
    private Integer ratingId;

    @JsonProperty("rating score")
    private Integer score;

    @JsonProperty("creation timestamp")
    private LocalDateTime createdAt;

    @JsonProperty("last updated timestamp")
    private LocalDateTime updatedAt;

    @JsonProperty("user identifier")
    private Long userId;

    @JsonProperty("content identifier")
    private Integer contentId;
}