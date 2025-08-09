package com.project.Health_BE.Service;

import com.project.Health_BE.Entity.RefreshTokenEntity;
import com.project.Health_BE.Entity.UserEntity;
import com.project.Health_BE.Repository.RefreshTokenRepository;
import com.project.Health_BE.Security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;

    public String createNewAccessToken(String refreshToken){
        // 토큰 유효성 검사에 실패하면 예외 발생
        if(!jwtTokenProvider.validateToken(refreshToken)){
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }
        RefreshTokenEntity refreshTokenFromDb=refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 리프레시 토큰입니다."));
        Long userId = refreshTokenFromDb.getUser().getUserId();
        UserEntity user=userService.findbyId(userId);
        return jwtTokenProvider.generateAccessToken(user.getEmail(), user.getUserId());
    }
}
