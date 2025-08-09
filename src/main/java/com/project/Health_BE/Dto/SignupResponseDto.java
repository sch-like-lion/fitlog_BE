package com.project.Health_BE.Dto;

import com.project.Health_BE.Entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SignupResponseDto {
    private Long user_id;
    private String customId;
    private String nickname;
    private double height;
    private double weight;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void DtofromEntity(UserEntity entity) {
        user_id = entity.getUser_id();
        customId = entity.getCustomId();
        nickname = entity.getNickname();
        height = entity.getHeight();
        weight = entity.getWeight();
        createdAt = entity.getCreated_at();
        updatedAt = entity.getUpdated_at();
    }
}
