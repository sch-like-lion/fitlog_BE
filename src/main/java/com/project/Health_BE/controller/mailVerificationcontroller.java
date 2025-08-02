package com.project.Health_BE.controller;

import com.project.Health_BE.Dto.getCode;
import com.project.Health_BE.Dto.getMail;
import com.project.Health_BE.Dto.mailVerificationDto;
import com.project.Health_BE.service.mailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class mailVerificationcontroller {
    private final mailVerificationService mailVerificationService;

    @PostMapping("/api/email")
    public String Sendmail(@RequestBody getMail email) {
        return mailVerificationService.Sendmail(email.getEmail());
    }

    @GetMapping("/api/email")
    public mailVerificationDto Verificationmail(@RequestBody getCode code) {
        return mailVerificationService.Verification(code.getCode());
    }
}
