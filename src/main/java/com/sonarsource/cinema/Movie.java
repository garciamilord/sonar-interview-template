package com.sonarsource.cinema;

import java.math.BigDecimal;
import java.util.List;
public record Movie(
        int id,
        String title,
        List<String> categories,
        int rating,
        int duration,
        BigDecimal price,
        String plot
) {}
