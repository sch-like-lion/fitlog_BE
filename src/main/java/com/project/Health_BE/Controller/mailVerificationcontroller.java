package com.project.Health_BE.Controller;

import com.project.Health_BE.Dto.getMail;
import com.project.Health_BE.Dto.mailVerificationDto;
import com.project.Health_BE.Exception.UserNotFoundException;
import com.project.Health_BE.Service.mailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
public class mailVerificationcontroller {
    private final mailVerificationService mailVerificationService;

    @PostMapping("/api/email")
    public ResponseEntity<?> Sendmail(@RequestBody getMail email) {
        try{
            return ResponseEntity.ok(mailVerificationService.Sendmail(email.getEmail()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이메일이 수신되지 않았습니다." + email.getEmail());
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("전송실패 ");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("전송실패 ");
        }
    }

    @GetMapping("/api/email")
    public ResponseEntity<?> Verificationmail(@RequestBody mailVerificationDto request) {
        try{
            return ResponseEntity.ok(mailVerificationService.Verification(request));
        } catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
