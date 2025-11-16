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
public class CategoryResponse {

    @JsonProperty("category identifier")
    private Integer categoryId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;
}