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
public class ContentCategoryResponse {

    @JsonProperty("content category identifier")
    private Integer contentCategoryId;

    @JsonProperty("content identifier")
    private Integer contentId;

    @JsonProperty("category identifier")
    private Integer categoryId;

    @JsonProperty("category score")
    private Double score;
}