package com.sonarsource.cinema;

import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieCsvReaderTest {

    @Test
    void shouldReadMoviesFromCsv() throws Exception {
        String csv = """
            ID;Title;Category;Rating;Duration;Price;Plot
            0;Mars Ghosts;"Action","SciFi";1;62;16;Spooky stuff
            1;White Eye;"Action","Drama";2;84;17;Strange looks
            """;

        Path tempFile = Files.createTempFile("movies", ".csv");
        Files.writeString(tempFile, csv);

        MovieCsvReader reader = new MovieCsvReader();

        List<Movie> movies = reader.read(tempFile.toUri().toString());

        // ... rest unchanged
        assertEquals(2, movies.size());

        Movie firstMovie = movies.get(0);
        assertEquals(0, firstMovie.id());
        assertEquals("Mars Ghosts", firstMovie.title());
        assertEquals(List.of("Action", "SciFi"), firstMovie.categories());
        assertEquals(1, firstMovie.rating());
        assertEquals(62, firstMovie.duration());
        assertEquals(new BigDecimal("16"), firstMovie.price());
        assertEquals("Spooky stuff", firstMovie.plot());

        Movie secondMovie = movies.get(1);
        assertEquals(1, secondMovie.id());
        assertEquals("White Eye", secondMovie.title());
        assertEquals(List.of("Action", "Drama"), secondMovie.categories());
        assertEquals(2, secondMovie.rating());
        assertEquals(84, secondMovie.duration());
        assertEquals(new BigDecimal("17"), secondMovie.price());
        assertEquals("Strange looks", secondMovie.plot());
    }
}

