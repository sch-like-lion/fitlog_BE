package com.project.Health_BE.Dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CalenderGet {
    private Long userId;
    private LocalDate date;
}
