package com.project.Health_BE.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "exercise_set_log")
@Getter
@Setter
@NoArgsConstructor
public class ExerciseSetLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "set_id")
    private Long setId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "log_id", nullable = false)
    private ExerciseLog exerciseLog;

    @Column(name = "set_number", nullable = false)
    private int setNumber;

    @Column(name = "weight_kg", precision = 5, scale = 2)
    private BigDecimal weightKg;

    @Builder
    public ExerciseSetLog(ExerciseLog exerciseLog, int setNumber, BigDecimal weightKg) {
        this.exerciseLog = exerciseLog;
        this.setNumber = setNumber;
        this.weightKg = weightKg;
    }
}