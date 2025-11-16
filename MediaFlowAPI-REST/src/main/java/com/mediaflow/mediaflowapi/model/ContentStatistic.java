package com.mediaflow.mediaflowapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "content_statistics")
public class ContentStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_statistic_id")
    @JsonProperty("content statistic identifier")
    private Integer contentStatisticId;

    @Column(name = "views")
    @JsonProperty("views count")
    private Integer views;

    @Column(name = "likes")
    @JsonProperty("like count")
    private Integer likes;

    @Column(name = "dislikes")
    @JsonProperty("dislike count")
    private Integer dislikes;

    @Column(name = "comments_count")
    @JsonProperty("comment Count")
    private Integer commentsCount;

    @Column(name = "average_watch_seconds")
    @JsonProperty("average Watch duration Seconds")
    private Double averageWatchSeconds;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", referencedColumnName = "content_id", unique = true)
    @JsonProperty("content identifier")
    private Content content;
}