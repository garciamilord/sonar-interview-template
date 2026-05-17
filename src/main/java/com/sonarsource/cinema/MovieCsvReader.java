//package com.sonarsource.cinema;
//
//import com.opencsv.CSVParser;
//import com.opencsv.CSVParserBuilder;
//import com.opencsv.CSVReader;
//import com.opencsv.CSVReaderBuilder;
//import com.opencsv.exceptions.CsvException;
//import com.opencsv.exceptions.CsvValidationException;
//
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.math.BigDecimal;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//import java.util.Arrays;
//import java.util.List;
//
//public class MovieCsvReader {
//
//    public List<Movie> read(String csvUrl) {
//        try (
//                InputStreamReader streamReader = new InputStreamReader(
//                        new URL(csvUrl).openStream(),
//                        StandardCharsets.UTF_8
//                );
//                CSVReader csvReader = new CSVReaderBuilder(streamReader)
//                        .withSkipLines(1)
//                        .withCSVParser(buildParser())
//                        .build()
//        ) {
//            return csvReader.readAll().stream()
//                    .map(this::toMovie)
//                    .toList();
//        } catch (IOException | CsvException e) {
//            throw new RuntimeException("Failed to read movies CSV", e);
//        }
//    }
//
//    private Movie toMovie(String[] row) {
//        return new Movie(
//                Integer.parseInt(row[0].trim()),
//                row[1].trim(),
//                parseCategories(row[2]),
//                Integer.parseInt(row[3].trim()),
//                Integer.parseInt(row[4].trim()),
//                new BigDecimal(row[5].trim()),
//                row[6].trim()
//        );
//    }
//
//    private List<String> parseCategories(String rawCategories) {
//        return Arrays.stream(rawCategories.replace("\"", "").split(","))
//                .map(String::trim)
//                .toList();
//    }
//
//    private CSVParser buildParser() {
//        return new CSVParserBuilder()
//                .withSeparator(';')
//                .withIgnoreQuotations(false)
//                .build();
//    }
//}
package com.sonarsource.cinema;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class MovieCsvReader {

    public List<Movie> read(String csvUrl) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new URL(csvUrl).openStream(), StandardCharsets.UTF_8)
        )) {
            return reader.lines()
                    .skip(1)
                    .map(this::toMovie)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read movies CSV", e);
        }
    }

    private Movie toMovie(String line) {
        String[] row = line.split(";", 7);

        if (row.length != 7) {
            throw new IllegalArgumentException("Invalid CSV row: " + line);
        }

        return new Movie(
                Integer.parseInt(row[0].trim()),
                row[1].trim(),
                parseCategories(row[2].trim()),
                Integer.parseInt(row[3].trim()),
                Integer.parseInt(row[4].trim()),
                new BigDecimal(row[5].trim()),
                row[6].trim()
        );
    }

    private List<String> parseCategories(String rawCategories) {
        return Arrays.stream(rawCategories.replace("\"", "").split(","))
                .map(String::trim)
                .toList();
    }
}
