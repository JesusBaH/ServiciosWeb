package com.mediaflow.MediaFlowAPI_GraphQL.graphql;

import com.mediaflow.MediaFlowAPI_GraphQL.dto.PlaybackHistoryGraphqlResponse;
import com.mediaflow.MediaFlowAPI_GraphQL.dto.PlaybackHistoryRequest;
import com.mediaflow.MediaFlowAPI_GraphQL.service.PlaybackHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PlaybackHistoryGraphql {

    private final PlaybackHistoryService playbackHistoryService;

    @QueryMapping
    public List<PlaybackHistoryGraphqlResponse> userHistory(
            @Argument Long userId,
            @Argument Optional<Integer> page,
            @Argument Optional<Integer> pageSize) {

        int actualPage = page.orElse(0);
        int actualPageSize = pageSize.orElse(10);

        if (actualPage < 0 || actualPageSize < 0 || (actualPage == 0 && actualPageSize == 0)) {
            throw new IllegalArgumentException(
                    "Invalid pagination parameters: page and pageSize cannot be negative and cannot both be 0.");
        }

        return playbackHistoryService.getUserHistory(userId, actualPage, actualPageSize);
    }

    @MutationMapping
    public PlaybackHistoryGraphqlResponse createHistory(@Valid @Argument PlaybackHistoryRequest req) {
        return playbackHistoryService.createHistoryRecord(req);
    }

    @MutationMapping
    public Boolean deleteHistory(@Argument Long historyId) {
        playbackHistoryService.deleteHistoryRecord(historyId);
        return true;
    }

    @MutationMapping
    public Boolean clearUserHistory(@Argument Long userId) {
        playbackHistoryService.clearUserHistory(userId);
        return true;
    }
}