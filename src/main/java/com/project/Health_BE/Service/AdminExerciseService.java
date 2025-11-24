package com.project.Health_BE.Service;

import com.project.Health_BE.Dto.AdminExerciseRequestDto;
import com.project.Health_BE.Dto.AdminExerciseResponseDto;
import com.project.Health_BE.Entity.Exercise;
import com.project.Health_BE.Entity.ExerciseCategory;
import com.project.Health_BE.Repository.ExerciseCategoryRepository;
import com.project.Health_BE.Repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseCategoryRepository exerciseCategoryRepository;


    // C: 새로운 운동 생성
    public AdminExerciseResponseDto createExercise(AdminExerciseRequestDto dto) {
        ExerciseCategory category = exerciseCategoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리 ID입니다: " + dto.getCategoryId()));

        Exercise.UnitType unitType;
        try {
            unitType = Exercise.UnitType.valueOf(dto.getUnitType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 UnitType입니다. 'WEIGHT', 'REPS', 'TIME' 중 하나여야 합니다.");
        }

        Exercise exercise = new Exercise(category, dto.getName(), unitType);
        Exercise savedExercise = exerciseRepository.save(exercise);
        return new AdminExerciseResponseDto(savedExercise);
    }


    // R: 모든 운동 조회
    @Transactional(readOnly = true)
    public List<AdminExerciseResponseDto> getAllExercises() {
        return exerciseRepository.findAll().stream()
                .map(AdminExerciseResponseDto::new)
                .collect(Collectors.toList());
    }


    // U: 운동 정보 수정
    public AdminExerciseResponseDto updateExercise(Long exerciseId, AdminExerciseRequestDto dto) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 운동 ID입니다: " + exerciseId));

        ExerciseCategory category = exerciseCategoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리 ID입니다: " + dto.getCategoryId()));

        Exercise.UnitType unitType;
        try {
            unitType = Exercise.UnitType.valueOf(dto.getUnitType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 UnitType입니다. 'WEIGHT', 'REPS', 'TIME' 중 하나여야 합니다.");
        }

        exercise.update(category, dto.getName(), unitType);
        Exercise updatedExercise = exerciseRepository.save(exercise);
        return new AdminExerciseResponseDto(updatedExercise);
    }


    // D: 운동 삭제
    public void deleteExercise(Long exerciseId) {
        if (!exerciseRepository.existsById(exerciseId)) {
            throw new IllegalArgumentException("존재하지 않는 운동 ID입니다: " + exerciseId);
        }
        // TODO: 이 운동을 참조하는 ExerciseLog가 있다면 삭제 정책을 결정해야 합니다.
        //       (현재는 그냥 삭제되지만, ExerciseLog가 참조하고 있다면 외래 키 제약 조건 오류가 발생할 수 있습니다.)
        exerciseRepository.deleteById(exerciseId);
    }
}