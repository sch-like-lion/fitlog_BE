package com.project.Health_BE.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupRequestDto {
    private Long user_id;
    private String nickname;
    private String customId;
    private String password;
    private String email;
    private int height;
    private int weight;
}
