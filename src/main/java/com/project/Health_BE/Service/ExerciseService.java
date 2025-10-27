package com.project.Health_BE.Service;

import com.project.Health_BE.Dto.*;
import com.project.Health_BE.Entity.Exercise;
import com.project.Health_BE.Entity.ExerciseLog;
import com.project.Health_BE.Entity.ExerciseSetLog;
import com.project.Health_BE.Entity.UserEntity;
import com.project.Health_BE.Repository.ExerciseCategoryRepository;
import com.project.Health_BE.Repository.ExerciseLogRepository;
import com.project.Health_BE.Repository.ExerciseRepository;
import com.project.Health_BE.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExerciseService {

    private final ExerciseCategoryRepository exerciseCategoryRepository;
    private final ExerciseRepository exerciseRepository;
    private final ExerciseLogRepository exerciseLogRepository;
    private final UserRepository userRepository;

    public List<ExerciseCategoryDto> getAllCategories() {
        return exerciseCategoryRepository.findAll().stream()
                .map(ExerciseCategoryDto::new)
                .collect(Collectors.toList());
    }

    public List<ExerciseDto> getExercisesByCategory(Long categoryId) {
        return exerciseRepository.findByExerciseCategory_CategoryId(categoryId).stream()
                .map(ExerciseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long saveExerciseLog(ExerciseLogRequestDto requestDto, String userId) {
        UserEntity user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));

        Exercise exercise = exerciseRepository.findById(requestDto.getExerciseId())
                .orElseThrow(() -> new IllegalArgumentException("Exercise not found with id: " + requestDto.getExerciseId()));

        ExerciseLog exerciseLog = ExerciseLog.builder()
                .user(user)
                .exercise(exercise)
                .date(requestDto.getDate())
                .memo(requestDto.getMemo())
                .fatigueLevel(requestDto.getFatigueLevel())
                .build();

        for (ExerciseSetDto setDto : requestDto.getSets()) {
            ExerciseSetLog setLog = ExerciseSetLog.builder()
                    .exerciseLog(exerciseLog)
                    .setNumber(setDto.getSetNumber())
                    .weightKg(setDto.getWeightKg())
                    .build();
            exerciseLog.addExerciseSetLog(setLog);
        }

        ExerciseLog savedLog = exerciseLogRepository.save(exerciseLog);
        return savedLog.getLogId();
    }

    public List<ExerciseLogResponseDto> getExerciseLogsByDate(String userId, LocalDate date) {
        UserEntity user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));

        return exerciseLogRepository.findByUser_UserIdAndDate(user.getUserId(), date).stream()
                .map(ExerciseLogResponseDto::new)
                .collect(Collectors.toList());
    }
}