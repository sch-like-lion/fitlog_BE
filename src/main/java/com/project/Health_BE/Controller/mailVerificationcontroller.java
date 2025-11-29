package com.project.Health_BE.Controller;

import com.project.Health_BE.Dto.getMail;
import com.project.Health_BE.Dto.mailVerificationDto;
import com.project.Health_BE.Service.mailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class mailVerificationcontroller {
    private final mailVerificationService mailVerificationService;

    @PostMapping("/api/email")
    public String Sendmail(@RequestBody getMail email) {
        try{
            return mailVerificationService.Sendmail(email.getEmail());
        } catch (IllegalArgumentException e) {
            return "이메일이 수신되지 않았습니다." + email.getEmail();
        } catch (RuntimeException e){
            return "전송실패 " + e;
        } catch (Exception e) {
            return "전송실패" + e;
        }
    }

    @GetMapping("/api/email")
    public mailVerificationDto Verificationmail(@RequestBody mailVerificationDto request) {
        return mailVerificationService.Verification(request);
    }
}
