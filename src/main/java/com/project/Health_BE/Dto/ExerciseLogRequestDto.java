package com.project.Health_BE.Dto;

import lombok.Getter;
import java.time.LocalDate;
import java.util.List;

@Getter
public class ExerciseLogRequestDto {
    private Long exerciseId;
    private LocalDate date;
    private String memo;
    private int fatigueLevel;
    private List<ExerciseSetDto> sets;
}