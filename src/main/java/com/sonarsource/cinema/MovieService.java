package com.sonarsource.cinema;

import java.util.List;
import java.util.Locale;

public class MovieService {

    public List<Movie> filterMovies(
            List<Movie> movies,
            String titleFilter,
            Integer ratingFilter,
            Integer durationFilter
    ) {
        return movies.stream()
                .filter(movie -> matchesTitle(movie, titleFilter))
                .filter(movie -> matchesRating(movie, ratingFilter))
                .filter(movie -> matchesDuration(movie, durationFilter))
                .toList();
    }

    public int count(List<Movie> movies) {
        return movies.size();
    }

    public double averageRating(List<Movie> movies) {
        if (movies.isEmpty()) {
            return 0.0;
        }

        return movies.stream()
                .mapToInt(Movie::rating)
                .average()
                .orElse(0.0);
    }

    private boolean matchesTitle(Movie movie, String titleFilter) {
        if (titleFilter == null || titleFilter.isBlank()) {
            return true;
        }

        return movie.title().toLowerCase(Locale.ROOT)
                .contains(titleFilter.toLowerCase(Locale.ROOT));
    }

    private boolean matchesRating(Movie movie, Integer ratingFilter) {
        return ratingFilter == null || movie.rating() == ratingFilter;
    }

    private boolean matchesDuration(Movie movie, Integer durationFilter) {
        return durationFilter == null || movie.duration() == durationFilter;
    }
}

