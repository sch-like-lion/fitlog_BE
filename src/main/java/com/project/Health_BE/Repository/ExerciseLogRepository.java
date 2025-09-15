package com.project.Health_BE.Repository;

import com.project.Health_BE.Entity.ExerciseLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseLogRepository extends JpaRepository<ExerciseLog, Long> {
}