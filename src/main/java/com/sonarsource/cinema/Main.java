package com.sonarsource.cinema;

public class Main {
  public static void main(String[] args) {
    System.out.println("Welcome to SonarSource Cinema!");
    printMovies();
  }

  public static void printMovies() {
    CsvParser csvParser = new CsvParser();
    csvParser.parseCsv("https://raw.githubusercontent.com/aurelien-poscia-sonarsource/sonar-cinema/refs/heads/main/src/main/resources/food.csv");
  }
}
