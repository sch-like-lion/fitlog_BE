package com.project.Health_BE.Dto;

import com.project.Health_BE.Entity.ExerciseCategory;
import lombok.Getter;

@Getter
public class ExerciseCategoryDto {
    private Long categoryId;
    private String name;

    public ExerciseCategoryDto(ExerciseCategory entity) {
        this.categoryId = entity.getCategoryId();
        this.name = entity.getName();
    }
}