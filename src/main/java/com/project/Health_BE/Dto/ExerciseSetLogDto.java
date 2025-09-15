package com.project.Health_BE.Dto;

import com.project.Health_BE.Entity.ExerciseSetLog;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ExerciseSetLogDto {
    private int setNumber;
    private BigDecimal weightKg;

    public ExerciseSetLogDto(ExerciseSetLog entity) {
        this.setNumber = entity.getSetNumber();
        this.weightKg = entity.getWeightKg();
    }
}