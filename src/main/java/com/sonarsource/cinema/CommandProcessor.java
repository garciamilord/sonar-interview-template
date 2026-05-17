package com.sonarsource.cinema;

import java.util.List;
/**
 * no internal movies field
 * processor no longer owns application data
 * parsing result is explicit with CommandRequest
 * less stringly-typed logic
 * easier for another engineer to extend
 * **/

import java.util.ArrayList;

public class CommandProcessor {
    private final MovieService movieService;

    public CommandProcessor(MovieService movieService) {
        this.movieService = movieService;
    }

    public String process(String input, List<Movie> movies) {
        CommandRequest request = parse(input);

        if (request.shouldExit()) {
            return "Bye!";
        }

        List<Movie> filteredMovies = movieService.filterMovies(
                movies,
                request.titleFilter(),
                request.ratingFilter(),
                request.durationFilter()
        );

        List<String> responses = new ArrayList<>();

        for (AggregationType aggregationType : request.aggregationTypes()) {
            switch (aggregationType) {
                case COUNT -> responses.add("Count: " + movieService.count(filteredMovies));
                case AVERAGE_RATING -> responses.add(
                        String.format("Average rating: %.4f", movieService.averageRating(filteredMovies))
                );
            }
        }

        return String.join(System.lineSeparator(), responses);
    }

    private CommandRequest parse(String input) {
        String trimmed = input.trim();

        if (trimmed.equalsIgnoreCase("EXIT")) {
            return CommandRequest.exit();
        }

        String[] tokens = trimmed.split("\\s+");

        List<AggregationType> aggregationTypes = new ArrayList<>();
        String titleFilter = null;
        Integer ratingFilter = null;
        Integer durationFilter = null;

        for (String token : tokens) {
            if (token.equalsIgnoreCase("COUNT")) {
                aggregationTypes.add(AggregationType.COUNT);
            } else if (token.equalsIgnoreCase("AVERAGE_RATING")) {
                aggregationTypes.add(AggregationType.AVERAGE_RATING);
            } else if (token.startsWith("FILTER_TITLE=")) {
                titleFilter = token.substring("FILTER_TITLE=".length());
            } else if (token.startsWith("FILTER_RATING=")) {
                ratingFilter = Integer.parseInt(token.substring("FILTER_RATING=".length()));
            } else if (token.startsWith("FILTER_DURATION=")) {
                durationFilter = Integer.parseInt(token.substring("FILTER_DURATION=".length()));
            } else {
                throw new IllegalArgumentException("Unknown command: " + token);
            }
        }

        if (aggregationTypes.isEmpty()) {
            throw new IllegalArgumentException("Missing aggregation command");
        }

        return new CommandRequest(
                aggregationTypes,
                titleFilter,
                ratingFilter,
                durationFilter,
                false
        );
    }
}
