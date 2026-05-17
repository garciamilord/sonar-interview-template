package com.sonarsource.cinema;

import java.util.List;
import java.util.Scanner;

public class Main {

  private static final String CSV_URL =
          "https://raw.githubusercontent.com/pierre-guillot-gh/movies/main/movies.csv";

  public static void main(String[] args) {
    System.out.println("Welcome to SonarSource Cinema!");

    MovieCsvReader movieCsvReader = new MovieCsvReader();
    List<Movie> movies = movieCsvReader.read(CSV_URL);

    MovieService movieService = new MovieService();
    CommandProcessor commandProcessor = new CommandProcessor(movieService);

    Scanner scanner = new Scanner(System.in);

    while (true) {
      String input = scanner.nextLine();

      try {
        String response = commandProcessor.process(input, movies);
        System.out.println(response);

        if ("EXIT".equalsIgnoreCase(input.trim())) {
          break;
        }
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
//TODO: DELETE
//public class Main {
//  public static void main(String[] args) {
//    System.out.println("Welcome to SonarSource Cinema!");
//    printMovies();
//  }
//
//  public static void printMovies() {
//    CsvParser csvParser = new CsvParser();
//    csvParser.parseCsv("https://raw.githubusercontent.com/pierre-guillot-gh/movies/main/movies.csv");
//  }
//}
