package com.mediaflow.MediaFlowAPI_GraphQL.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContentRequest {

    @NotBlank(message = "Title cannot be blank")
    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @NotNull(message = "User ID (creator) cannot be null")
    @JsonProperty("user identifier")
    private Integer userId;

    @NotBlank(message = "Storage URL cannot be blank")
    @JsonProperty("storage url")
    private String storageUrl;

    @NotBlank(message = "Thumbnail URL cannot be blank")
    @JsonProperty("thumbnail url")
    private String thumbnailUrl;
}