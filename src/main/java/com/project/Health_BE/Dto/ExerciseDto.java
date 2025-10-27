package com.project.Health_BE.Dto;

import com.project.Health_BE.Entity.Exercise;
import lombok.Getter;

@Getter
public class ExerciseDto {
    private Long exerciseId;
    private String name;
    private String unitType;

    public ExerciseDto(Exercise entity) {
        this.exerciseId = entity.getExerciseId();
        this.name = entity.getName();
        this.unitType = entity.getUnitType().name();
    }
}