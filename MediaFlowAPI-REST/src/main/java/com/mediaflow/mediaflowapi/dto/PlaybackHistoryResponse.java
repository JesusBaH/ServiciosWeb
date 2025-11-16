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
public class PlaybackHistoryResponse {

    @JsonProperty("history identifier")
    private Long playbackHistoryId;

    @JsonProperty("user identifier")
    private Long userId;

    @JsonProperty("content identifier")
    private Integer contentId;

    @JsonProperty("watched duration seconds")
    private Integer watchedSeconds;

    @JsonProperty("user agent")
    private String userAgent;

    @JsonProperty("viewed timestamp")
    private LocalDateTime viewedAt;
}