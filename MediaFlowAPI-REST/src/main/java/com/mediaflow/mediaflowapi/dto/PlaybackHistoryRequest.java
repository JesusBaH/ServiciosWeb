package com.mediaflow.mediaflowapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaybackHistoryRequest {
    
    @NotNull(message = "User ID cannot be null.")
    @JsonProperty("user identifier")
    private Long userId; 

    @NotNull(message = "Content ID cannot be null.")
    @JsonProperty("content identifier")
    private Integer contentId;

    @NotNull(message = "Watched seconds cannot be null.")
    @PositiveOrZero(message = "Watched seconds must be zero or positive.")
     @JsonProperty("watched duration seconds")
    private Integer watchedSeconds;

    @NotBlank(message = "User agent cannot be blank.")
     @JsonProperty("user agent")
    private String userAgent;
}