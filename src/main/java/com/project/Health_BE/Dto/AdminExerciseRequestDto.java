package com.project.Health_BE.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminExerciseRequestDto {
    private String name;
    private Long categoryId;
    private String unitType; // "WEIGHT", "REPS", "TIME" 중 하나
}