package com.project.Health_BE.Repository;

import com.project.Health_BE.Entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Optional<Exercise> findbyname(String name);
    List<Exercise> findByExerciseCategory_CategoryId(Long categoryId);
}
