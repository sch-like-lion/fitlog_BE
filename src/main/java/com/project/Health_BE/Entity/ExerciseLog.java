package com.project.Health_BE.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exercise_log")
@Getter
@NoArgsConstructor
public class ExerciseLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "memo")
    private String memo;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "fatigue_level", nullable = false)
    private int fatigueLevel;

    @OneToMany(mappedBy = "exerciseLog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExerciseSetLog> exerciseSetLogs = new ArrayList<>();

    @Builder
    public ExerciseLog(UserEntity user, Exercise exercise, LocalDate date, String memo, int fatigueLevel) {
        this.user = user;
        this.exercise = exercise;
        this.date = date;
        this.memo = memo;
        this.fatigueLevel = fatigueLevel;
        this.createdAt = LocalDateTime.now();
    }

    public void addExerciseSetLog(ExerciseSetLog exerciseSetLog) {
        exerciseSetLogs.add(exerciseSetLog);
        exerciseSetLog.setExerciseLog(this);
    }
}
