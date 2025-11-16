package com.mediaflow.mediaflowapi.model;

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
@Table (name="ratings")
public class Rating {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    @JsonProperty("rating identifier")
    private Integer ratingId;

    @Column(name = "score")
    @JsonProperty("rating score")
    private Integer score;

    @Column(name = "created_at", updatable = false)
    @JsonProperty("creation timestamp")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonProperty("last updated timestamp")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonProperty("user identifier")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", referencedColumnName = "content_id")
    @JsonProperty("content identifier")
    private Content content;
}
