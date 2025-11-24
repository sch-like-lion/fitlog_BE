package com.project.Health_BE.Dto;

import com.project.Health_BE.Entity.RankEntity;
import com.project.Health_BE.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
@AllArgsConstructor
public class UserRankInfoResponseDto {

    private Long userId;
    private String nickname;
    private Integer totalScore;
    private String tierName;
    private String tierImageUrl;
    private Long overallRank;
    private Long rankInTier;
//todo: 승준  - dto 수정 필요
    public UserRankInfoResponseDto(UserEntity user, RankEntity tier, Long overallRank, Long rankInTier) {
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.totalScore = user.getTotalPoint();
        this.tierName = tier.getTierName();
        this.tierImageUrl = tier.getTierImageUrl();
        this.overallRank = overallRank;
        this.rankInTier = rankInTier;
    }
}
