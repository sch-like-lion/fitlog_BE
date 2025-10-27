package com.project.Health_BE.Controller;

import com.project.Health_BE.Dto.CalenderDto;
import com.project.Health_BE.Dto.CalenderGet;
import com.project.Health_BE.Exception.UserNotFoundException;
import com.project.Health_BE.Service.CalenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class ExerciseController {
    private final CalenderService calenderService;

    @PostMapping("/api/calender")
    public ResponseEntity<?> postcalender(@RequestBody CalenderDto request) {
        try{
            return ResponseEntity.ok(Collections.singletonMap("message", "기록성공 userId: "+calenderService.post(request).toString()));
        }
        catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", e.getMessage()));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("type", "입력 자료형이 잘못됨"));
        }
    }

    @GetMapping("/api/calender")
    public ResponseEntity<?> getcalender(@RequestParam CalenderGet request) {
        try {
            return ResponseEntity.ok(calenderService.get(request));
        }
        catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", e.getMessage()));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", "서버 에러"));
        }
    }
}
