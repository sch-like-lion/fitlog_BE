package com.project.Health_BE.Dto;

import com.project.Health_BE.Entity.ExerciseLog;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ExerciseLogResponseDto {
    private Long logId;
    private String exerciseName;
    private LocalDate date;
    private String memo;
    private int fatigueLevel;
    private List<ExerciseSetLogDto> sets;

    public ExerciseLogResponseDto(ExerciseLog entity) {
        this.logId = entity.getLogId();
        this.exerciseName = entity.getExercise().getName();
        this.date = entity.getDate();
        this.memo = entity.getMemo();
        this.fatigueLevel = entity.getFatigueLevel();
        this.sets = entity.getExerciseSetLogs().stream()
                .map(ExerciseSetLogDto::new)
                .collect(Collectors.toList());
    }
}