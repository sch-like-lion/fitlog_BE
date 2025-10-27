package com.project.Health_BE.Dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CalenderDto {
    private String exercise;
    private Long userId;
    private LocalDate date;
    private int weight;
    private int time;
    private int set;
    private int fatigue;
    private String memo;
}
