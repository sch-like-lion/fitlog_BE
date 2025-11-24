package com.project.Health_BE.Service;

import com.project.Health_BE.Dto.UserRankInfoResponseDto;
import com.project.Health_BE.Entity.RankEntity;
import com.project.Health_BE.Entity.UserEntity;
import com.project.Health_BE.Repository.RankRepository;
import com.project.Health_BE.Repository.UserRepository;
import com.project.Health_BE.Security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankService {
    private final UserRepository userRepository;
    private final RankRepository rankRepository;
    private final JwtTokenProvider jwtTokenProvider;

    //user티어, 점수
    @Transactional
    public UserRankInfoResponseDto getUserRankInfo(String jwtToken) {
        Long userId = jwtTokenProvider.getUserId(jwtToken);
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. userId=" + userId));

        int userScore = user.getTotalPoint();

        RankEntity currentTier = rankRepository.findTierByScore(userScore)
                .orElseThrow(() -> new IllegalArgumentException("해당 점수의 티어 정보를 찾을 수 없습니다."));

        Long overallRank = userRepository.findOverallRankByUserId(userId);

        int minPoint = currentTier.getMinScore();
        int maxPoint = rankRepository.findNextTier(minPoint)
                .map(nextTier -> nextTier.getMinScore() - 1)
                .orElse(Integer.MAX_VALUE);

        Long rankInTier = userRepository.findRankInTier(userId, minPoint, maxPoint);

        return UserRankInfoResponseDto.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .totalScore(user.getTotalPoint())
                .tierName(currentTier.getTierName())
                .tierImageUrl(currentTier.getTierImageUrl())
                .rankInTier(rankInTier)
                .overallRank(overallRank)
                .build();
    }
    // 전체 랭킹 조회
    @Transactional
    public UserRankInfoResponseDto getOverallRank(String jwtToken) {
        Long userId = jwtTokenProvider.getUserId(jwtToken);
        Long overallRank =  userRepository.findOverallRankByUserId(userId);
        return UserRankInfoResponseDto.builder()
                .overallRank(overallRank)
                .build();

    }
    //지역 내 랭킹 조회
    public UserRankInfoResponseDto getUserRankInfoByLocation(String jwtToken) {
        Long userId = jwtTokenProvider.getUserId(jwtToken);
        UserEntity user= userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. userId=" + userId));

        String myLocation = user.getLocation();
        Integer myScore = (user.getTotalPoint() != null) ? user.getTotalPoint() : 0;

        if (myLocation == null) {
            throw new IllegalStateException("지역 정보를 찾을 수 없습니다.");
        }

        Long higherScoreCount=userRepository.countByLocationAndTotalPointGreaterThan(myLocation, myScore);
        Long myRank= higherScoreCount + 1;

        return UserRankInfoResponseDto.builder()
                .overallRank(myRank)
                .build();
    }

    // 티어 내 랭킹 조회
    public UserRankInfoResponseDto getRankInTier(String jwtToken) {
        Long userId = jwtTokenProvider.getUserId(jwtToken);
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. userId=" + userId));

        int userScore = user.getTotalPoint();

        RankEntity currentTier = rankRepository.findTierByScore(userScore)
                .orElseThrow(() -> new IllegalArgumentException("해당 점수의 티어 정보를 찾을 수 없습니다."));

        int minPoint = currentTier.getMinScore();
        int maxPoint = rankRepository.findNextTier(minPoint)
                .map(nextTier -> nextTier.getMinScore() - 1)
                .orElse(Integer.MAX_VALUE);

        Long rankInTier = userRepository.findRankInTier(userId, minPoint, maxPoint);

        return UserRankInfoResponseDto.builder()
                .rankInTier(rankInTier)
                .build();
    }
}
