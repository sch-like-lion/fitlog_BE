package com.project.Health_BE.Controller;

import com.project.Health_BE.Dto.AdminExerciseRequestDto;
import com.project.Health_BE.Dto.AdminExerciseResponseDto;
import com.project.Health_BE.Service.AdminExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/exercises")
@RequiredArgsConstructor
public class AdminExerciseController {

    private final AdminExerciseService adminExerciseService;

    // 운동 생성
    @PostMapping
    public ResponseEntity<AdminExerciseResponseDto> createExercise(@RequestBody AdminExerciseRequestDto requestDto) {
        AdminExerciseResponseDto responseDto = adminExerciseService.createExercise(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 운동 전체 조회
    @GetMapping
    public ResponseEntity<List<AdminExerciseResponseDto>> getAllExercises() {
        List<AdminExerciseResponseDto> exercises = adminExerciseService.getAllExercises();
        return ResponseEntity.ok(exercises);
    }

    // 운동 수정
    @PutMapping("/{id}")
    public ResponseEntity<AdminExerciseResponseDto> updateExercise(@PathVariable Long id, @RequestBody AdminExerciseRequestDto requestDto) {
        AdminExerciseResponseDto responseDto = adminExerciseService.updateExercise(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 운동 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteExercise(@PathVariable Long id) {
        adminExerciseService.deleteExercise(id);
        return ResponseEntity.ok(Map.of("message", "운동이 성공적으로 삭제되었습니다. (ID: " + id + ")"));
    }
}