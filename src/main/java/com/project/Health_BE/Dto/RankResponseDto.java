package com.project.Health_BE.Dto;

import com.project.Health_BE.Entity.RankEntity;
import com.project.Health_BE.Entity.UserEntity;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RankResponseDto {
    public String rankName;
    private String rankImage;
    private int totalScore;
    public RankResponseDto(RankEntity rankEntity, UserEntity userEntity) {
        this.rankImage = rankEntity.getTierImageUrl();
        this.rankName = rankEntity.getTierName();
        this.totalScore = userEntity.getTotalPoint();
    }
}
