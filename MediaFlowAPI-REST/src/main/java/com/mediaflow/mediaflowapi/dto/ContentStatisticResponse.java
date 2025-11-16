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
public class ContentStatisticResponse {

    @JsonProperty("content statistic identifier")
    private Integer contentStatisticId;

    @JsonProperty("content identifier")
    private Integer contentId;

    @JsonProperty("views count")
    private Integer views;

    @JsonProperty("like count")
    private Integer likes;

    @JsonProperty("dislike count")
    private Integer dislikes;

    @JsonProperty("comment Count")
    private Integer commentsCount;

    @JsonProperty("average Watch duration Seconds")
    private Double averageWatchSeconds;
}