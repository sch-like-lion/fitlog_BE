package com.project.Health_BE.Dto;

import com.project.Health_BE.Entity.Exercise;
import lombok.Getter;

@Getter
public class AdminExerciseResponseDto {
    private Long exerciseId;
    private String name;
    private String unitType;
    private Long categoryId;
    private String categoryName;

    public AdminExerciseResponseDto(Exercise entity) {
        this.exerciseId = entity.getExerciseId();
        this.name = entity.getName();
        this.unitType = entity.getUnitType().name();
        if (entity.getExerciseCategory() != null) {
            this.categoryId = entity.getExerciseCategory().getCategoryId();
            this.categoryName = entity.getExerciseCategory().getName();
        }
    }
}