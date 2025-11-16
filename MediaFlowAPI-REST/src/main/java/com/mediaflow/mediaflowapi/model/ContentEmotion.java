package com.mediaflow.mediaflowapi.model;

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
@Table (name="content_emotions")
public class ContentEmotion {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_emotion_id")
    @JsonProperty("content emotion identifier")
    private Integer contentEmotionId;

    @Column(name = "score")
    @JsonProperty("emotion score")
    private Double score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emotion_id", referencedColumnName = "emotion_id")
    @JsonProperty("emotion")
    private Emotion emotion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", referencedColumnName = "content_id")
    @JsonProperty("content identifier")
    private Content content;
}



