package com.project.Health_BE.Dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CalenderResponseDto {
    private String exercise;
    private Long userId;
    private LocalDate date;
    private int weight;
    private int time;
    private int set;
    private int fatigue;
    private String memo;
    private int grass;

    @Builder
    public CalenderResponseDto(String exercise, Long userId, LocalDate date, int weight,
                          int time, int set, int fatigue, String memo, int grass) {
        this.exercise = exercise;
        this.userId = userId;
        this.date = date;
        this.weight = weight;
        this.time = time;
        this.set = set;
        this.fatigue = fatigue;
        this.memo = memo;
        this.grass = grass;
    }
}
