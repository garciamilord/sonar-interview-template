package com.sonarsource.cinema;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieServiceTest {

    @Test
    void shouldFilterByTitleContainingText() {
        List<Movie> movies = List.of(
                new Movie(1, "Batman Begins", List.of("Action"), 5, 90, new BigDecimal("10"), "Plot"),
                new Movie(2, "Superman", List.of("Action"), 4, 90, new BigDecimal("12"), "Plot")
        );

        MovieService service = new MovieService();

        List<Movie> result = service.filterMovies(movies, "Batman", null, null);

        assertEquals(1, result.size());
        assertEquals("Batman Begins", result.get(0).title());
    }

    @Test
    void shouldFilterByDuration() {
        List<Movie> movies = List.of(
                new Movie(1, "A", List.of("Action"), 5, 90, new BigDecimal("10"), "Plot"),
                new Movie(2, "B", List.of("Action"), 4, 120, new BigDecimal("12"), "Plot")
        );

        MovieService service = new MovieService();

        List<Movie> result = service.filterMovies(movies, null, null, 90);

        assertEquals(1, result.size());
        assertEquals("A", result.get(0).title());
    }

    @Test
    void shouldAverageRatingWithMultipleFilters() {
        List<Movie> movies = List.of(
                new Movie(1, "Christmas Night", List.of("Drama"), 3, 90, new BigDecimal("10"), "Plot"),
                new Movie(2, "Christmas Carol", List.of("Family"), 3, 95, new BigDecimal("12"), "Plot"),
                new Movie(3, "Christmas Action", List.of("Action"), 4, 100, new BigDecimal("11"), "Plot"),
                new Movie(4, "Batman Begins", List.of("Action"), 3, 100, new BigDecimal("11"), "Plot")
        );

        CommandProcessor processor = new CommandProcessor(new MovieService());

        String result = processor.process(
                "AVERAGE_RATING FILTER_RATING=3 FILTER_TITLE=Christmas",
                movies
        );

        assertEquals("Average rating: 3.0000", result);
    }


}
