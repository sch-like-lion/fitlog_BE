package com.project.Health_BE.Repository;

import com.project.Health_BE.Entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByExerciseCategory_CategoryId(Long categoryId);
}