package com.project.Health_BE.Controller;

import com.project.Health_BE.Dto.mailVerificationDto;
import com.project.Health_BE.Service.mailVerificationService;
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
    public String Sendmail(@RequestBody String email) {
        return mailVerificationService.Sendmail(email);
    }

    @GetMapping("/api/email")
    public mailVerificationDto Verificationmail(@RequestBody String code) {
        return mailVerificationService.Verification(code);
    }
}
