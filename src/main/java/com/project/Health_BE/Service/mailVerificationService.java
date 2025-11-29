package com.project.Health_BE.Service;

import com.project.Health_BE.Dto.mailVerificationDto;
import com.project.Health_BE.Entity.Emailverification;
import com.project.Health_BE.Exception.UserNotFoundException;
import com.project.Health_BE.Repository.EmailverificatonRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
        MimeMessage message = mailSender.createMimeMessage();
        Random code = new Random();
        mailDto.setEmail(email);

        code.setSeed(System.currentTimeMillis());
        mailDto.setVerificationCode(String.valueOf(code.nextInt(9000)+1000));

        if(email == null || email.isBlank()){
            throw new IllegalArgumentException("수신 이메일이 없습니다.");
        }
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
            helper.setTo(email);
            helper.setFrom("fitlog0801@gmail.com");    //운영자로 사용할 이메일
            helper.setSubject("이메일 인증번호입니다");
            helper.setText("인증번호는: " + mailDto.getVerificationCode() + "입니다.");
            mailSender.send(message);
        }
        catch (Exception e) {
            throw new RuntimeException("전송 실패" + e);
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