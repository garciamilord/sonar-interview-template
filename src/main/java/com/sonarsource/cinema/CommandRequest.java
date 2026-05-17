package com.sonarsource.cinema;

import java.util.List;

//This makes command parsing much cleaner.
public record CommandRequest(
        List<AggregationType> aggregationTypes,
        String titleFilter,
        Integer ratingFilter,
        Integer durationFilter,
        boolean shouldExit   // renamed
) {
    public static CommandRequest exit() {
        return new CommandRequest(null, null, null, null, true);
    }
}
