package com.mediaflow.mediaflowapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ContentStatisticRequest {

    @NotNull(message = "Content ID cannot be null.")
    @JsonProperty("content identifier")
    private Integer contentId;

    @PositiveOrZero(message = "Views cannot be negative.")
    @JsonProperty("views count")
    private Integer views;

    @PositiveOrZero(message = "Likes cannot be negative.")
    @JsonProperty("like count")
    private Integer likes;

    @PositiveOrZero(message = "Dislikes cannot be negative.")
    @JsonProperty("dislike count")
    private Integer dislikes;

    @PositiveOrZero(message = "Comments count cannot be negative.")
    @JsonProperty("comment Count")
    private Integer commentsCount;

    @PositiveOrZero(message = "Average watch seconds cannot be negative.")
    @JsonProperty("average Watch duration Seconds")
    private Double averageWatchSeconds;
}