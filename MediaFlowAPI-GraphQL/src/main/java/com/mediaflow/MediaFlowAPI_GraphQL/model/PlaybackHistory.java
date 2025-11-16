package com.mediaflow.MediaFlowAPI_GraphQL.model;


import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table (name="playback_histories")
public class PlaybackHistory {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playback_history_id")
    @JsonProperty("history identifier")
    private Long playbackHistoryId;

    @Column(name = "watched_seconds")
    @JsonProperty("watched duration seconds")
    private Integer watchedSeconds;

    @Column(name = "user_agent")
    @JsonProperty("user agent")
    private String userAgent;

    @Column(name = "viewed_at")
    @JsonProperty("viewed timestamp")
    private LocalDateTime viewedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonProperty("user identifier")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", referencedColumnName = "content_id")
    @JsonProperty("content identifier")
    private Content content;

}