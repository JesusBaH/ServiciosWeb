package com.mediaflow.MediaFlowAPI_GraphQL.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentResponse {

    @JsonProperty("content identifier")
    private Integer contentId;

    @JsonProperty("title")
    private String title;
    
    @JsonProperty("content type")
    private String contentType;

    @JsonProperty("description")
    private String description;

    @JsonProperty("recommended age")
    private Integer recommendedAge;

    @JsonProperty("storage url")
    private String storageUrl;

    @JsonProperty("thumbnail url")
    private String thumbnailUrl;
    
    @JsonProperty("created")
    private LocalDateTime created;
}