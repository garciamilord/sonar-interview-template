package com.sonarsource.cinema;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandProcessorTest {

    @Test
    void shouldCountMoviesFilteredByDuration() {
        List<Movie> movies = List.of(
                new Movie(1, "A", List.of("Action"), 5, 90, new BigDecimal("10"), "Plot"),
                new Movie(2, "B", List.of("Action"), 4, 120, new BigDecimal("12"), "Plot")
        );

        CommandProcessor processor = new CommandProcessor(new MovieService());

        String result = processor.process("FILTER_DURATION=90 COUNT", movies);

        assertEquals("Count: 1", result);
    }

    @Test
    void shouldAverageRatingForMoviesMatchingTitle() {
        List<Movie> movies = List.of(
                new Movie(1, "Batman Begins", List.of("Action"), 5, 90, new BigDecimal("10"), "Plot"),
                new Movie(2, "Batman Returns", List.of("Action"), 3, 95, new BigDecimal("12"), "Plot"),
                new Movie(3, "Superman", List.of("Action"), 1, 100, new BigDecimal("11"), "Plot")
        );

        CommandProcessor processor = new CommandProcessor(new MovieService());

        String result = processor.process("AVERAGE_RATING FILTER_TITLE=Batman", movies);

        assertEquals("Average rating: 4.0000", result);
    }

    @Test
    void shouldSupportMultipleAggregationCommandsWithFilters() {
        List<Movie> movies = List.of(
                new Movie(1, "Christmas Night", List.of("Drama"), 3, 90, new BigDecimal("10"), "Plot"),
                new Movie(2, "Christmas Carol", List.of("Family"), 2, 95, new BigDecimal("12"), "Plot"),
                new Movie(3, "Batman Begins", List.of("Action"), 5, 100, new BigDecimal("11"), "Plot")
        );

        CommandProcessor processor = new CommandProcessor(new MovieService());

        String result = processor.process(
                "COUNT AVERAGE_RATING FILTER_TITLE=Christmas",
                movies
        );

        assertEquals("""
                Count: 2
                Average rating: 2.5000""", result);
    }

}
