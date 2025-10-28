package com.project.Health_BE.Repository;

import com.project.Health_BE.Entity.ExerciseLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExerciseLogRepository extends JpaRepository<ExerciseLog, Long> {
    List<ExerciseLog> findByUser_UserIdAndDate(Long userId, LocalDate date);
    Optional<ExerciseLog> findByDate(LocalDate date);
}