package com.mediaflow.MediaFlowAPI_GraphQL.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaybackHistoryGraphqlResponse {

    @JsonProperty("history identifier")
    private Long playbackHistoryId;

    @JsonProperty("watched duration seconds")
    private Integer watchedSeconds;

    @JsonProperty("user agent")
    private String userAgent;

    @JsonProperty("viewed timestamp")
    private LocalDateTime viewedAt;
    
    private UserResponse user; 
    
    private ContentResponse content; 
}