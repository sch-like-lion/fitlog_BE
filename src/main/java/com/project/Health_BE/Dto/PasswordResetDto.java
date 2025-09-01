package com.project.Health_BE.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetDto {
    private String email;          // 이메일 주소
    private String newPassword;    // 사용자가 입력한 새 비밀번호
    private String authCode;       // 이메일로 받은 인증번호
}
