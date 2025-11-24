package com.project.Health_BE.Service;

import com.project.Health_BE.Dto.mailVerificationDto;
import com.project.Health_BE.Entity.Emailverification;
import com.project.Health_BE.Exception.UserNotFoundException;
import com.project.Health_BE.Repository.EmailverificatonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class mailVerificationService {
    @Autowired
    private JavaMailSender mailSender;
    private final EmailverificatonRepository emailRepository;

    public String Sendmail(String email) {///사용자 email
        mailVerificationDto mailDto = new mailVerificationDto();
        SimpleMailMessage message = new SimpleMailMessage();
        Random code = new Random();
        mailDto.setEmail(email);

        code.setSeed(System.currentTimeMillis());
        mailDto.setVerificationCode(String.valueOf(code.nextInt(9000)+1000));

        try{
            message.setTo(email);
            message.setFrom("fitlog0801@gmail.com");    //운영자로 사용할 이메일
            message.setSubject("이메일 인증번호입니다");
            message.setText("인증번호는: " + mailDto.getVerificationCode() + "입니다.");
            mailSender.send(message);
        }
        catch (Exception e) {
            throw new RuntimeException("전송 실패" + email);
        }
        Emailverification entity = emailRepository.findByEmail(email)
                .orElseGet(() -> Emailverification.builder()
                        .email(email)
                        .code(mailDto.getVerificationCode())
                        .updatetime(LocalDateTime.now())
                        .verify(false)
                        .build()
                );
        emailRepository.save(entity);
        return "전송 완료" + email;
    }

    public mailVerificationDto Verification(mailVerificationDto dto) {

        Emailverification entity = emailRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new UserNotFoundException("email을 찾을 수 없습니다."));
        if (ChronoUnit.MINUTES.between(entity.getUpdatetime(), LocalDateTime.now()) > 5) throw new RuntimeException("인증코드가 만료되었습니다."); ///사용자가 입력한 코드 받기
        if(dto.getVerificationCode().equals(dto.getVerificationCode())) {
            entity.builder().verify(true).updatetime(LocalDateTime.now()).code(null);
            emailRepository.save(entity);
            dto.setMailcheck(true);
            return dto;
        }
        return dto;
    }

}