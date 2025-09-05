package com.project.Health_BE.Dto;

import com.project.Health_BE.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String message;
    private String token;

    public LoginResponseDto(String message) {
    }

    public void fromEntity(UserEntity user) {

    }
}
