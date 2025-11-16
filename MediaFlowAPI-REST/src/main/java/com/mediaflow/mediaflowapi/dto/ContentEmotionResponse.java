package com.mediaflow.mediaflowapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentEmotionResponse {

    @JsonProperty("content emotion identifier")
    private Integer contentEmotionId;

    @JsonProperty("content identifier")
    private Integer contentId;

    @JsonProperty("emotion identifier")
    private Integer emotionId;

    @JsonProperty("emotion score")
    private Double score;
}