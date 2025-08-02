package com.project.Health_BE.service;

import com.project.Health_BE.Dto.mailVerificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class mailVerificationService {
    @Autowired
    private JavaMailSender mailSender;
    private final mailVerificationDto mailDto = new mailVerificationDto();

    public String Sendmail(String email) { ///사용자 email
        mailDto.setEmail(email);
        Random code = new Random();
        code.setSeed(System.currentTimeMillis());
        mailDto.setVerificationCode(String.valueOf(code.nextInt(9000)+1000));
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("fitlog0801@gmail.com");    //운영자로 사용할 이메일
        message.setSubject("이메일 인증번호입니다");
        message.setText("인증번호는: " + mailDto.getVerificationCode() + "입니다.");
        mailSender.send(message);
        return "전송 완료" + email;
    }

    public mailVerificationDto Verification(String code) { ///사용자가 입력한 코드 받기
        if(mailDto.getVerificationCode().equals(code)) {
            mailDto.setMailcheck(true);
            return mailDto;
        }
        return mailDto;
    }
}
