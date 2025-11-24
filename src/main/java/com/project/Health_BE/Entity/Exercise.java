package com.project.Health_BE.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exercise")
@Getter
@NoArgsConstructor
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private Long exerciseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private ExerciseCategory exerciseCategory;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit_type", nullable = false)
    private UnitType unitType;

    public enum UnitType {
        WEIGHT, REPS, TIME
    }

    // 관리자 메뉴용 //
    // 운동 생성용
    public Exercise(ExerciseCategory exerciseCategory, String name, UnitType unitType) {
        this.exerciseCategory = exerciseCategory;
        this.name = name;
        this.unitType = unitType;
    }
    // 운동 업데이트용
    public void update(ExerciseCategory exerciseCategory, String name, UnitType unitType) {
        this.exerciseCategory = exerciseCategory;
        this.name = name;
        this.unitType = unitType;
    }
}