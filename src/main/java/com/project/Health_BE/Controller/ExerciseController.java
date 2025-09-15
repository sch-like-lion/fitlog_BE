package com.project.Health_BE.Controller;

import com.project.Health_BE.Dto.ExerciseCategoryDto;
import com.project.Health_BE.Dto.ExerciseDto;
import com.project.Health_BE.Dto.ExerciseLogRequestDto;
import com.project.Health_BE.Service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercise")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping("/categories")
    public ResponseEntity<List<ExerciseCategoryDto>> getAllCategories() {
        return ResponseEntity.ok(exerciseService.getAllCategories());
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<List<ExerciseDto>> getExercisesByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(exerciseService.getExercisesByCategory(categoryId));
    }

    @PostMapping("/log")
    public ResponseEntity<Long> saveExerciseLog(@RequestBody ExerciseLogRequestDto requestDto, @AuthenticationPrincipal User user) {
        Long logId = exerciseService.saveExerciseLog(requestDto, user.getUsername());
        return ResponseEntity.ok(logId);
    }
}