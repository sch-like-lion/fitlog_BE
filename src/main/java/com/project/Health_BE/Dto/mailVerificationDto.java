package com.project.Health_BE.Dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class mailVerificationDto {
    private String verificationCode;
    private String email;
    private boolean mailcheck;
}
