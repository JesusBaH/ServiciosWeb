package com.mediaflow.MediaFlowAPI_GraphQL.model;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "contents")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    @JsonProperty("content identifier")
    private Integer contentId;

    @Column(name = "format")
    @JsonProperty("format")
    private String format;

    @Column(name = "file_size")
    @JsonProperty("file size")
    private String fileSize;

    @Column(name = "language")
    @JsonProperty("language")
    private String language;

    @Column(name = "title")
    @JsonProperty("title")
    private String title;

    @Column(name = "content_type")
    @JsonProperty("content type")
    private String contentType;

    @Column(name = "description")
    @JsonProperty("description")
    private String description;

    @Column(name = "recommended_age")
    @JsonProperty("recommended age")
    private Integer recommendedAge;

    @Column(name = "storage_url")
    @JsonProperty("storage url")
    private String storageUrl;

    @Column(name = "thumbnail_url")
    @JsonProperty("thumbnail url")
    private String thumbnailUrl;

    @Column(name = "created")
    @JsonProperty("created")
    private LocalDateTime created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_id", referencedColumnName = "user_id")
    @JsonProperty("user identifier")
    private User user;

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaybackHistory> playbackHistories;
}