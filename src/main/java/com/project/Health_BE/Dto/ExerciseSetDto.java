package com.project.Health_BE.Dto;

import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class ExerciseSetDto {
    private int setNumber;
    private BigDecimal weightKg;
}