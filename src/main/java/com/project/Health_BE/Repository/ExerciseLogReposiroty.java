package com.project.Health_BE.Repository;

import com.project.Health_BE.Entity.Exercise;
import com.project.Health_BE.Entity.ExerciseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
@Repository
public interface ExerciseLogReposiroty extends JpaRepository<ExerciseLog, Long> {
    Optional<ExerciseLog> findbydate(LocalDate date);
}
