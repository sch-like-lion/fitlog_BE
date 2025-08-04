package com.project.Health_BE.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequestDto {
    private String customId;
    private String password;
    private String nickname;
    private String email;
    private Long user_id;
}
