package com.project.Health_BE.Service;

import com.project.Health_BE.Entity.RefreshTokenEntity;
import com.project.Health_BE.Entity.UserEntity;
import com.project.Health_BE.Repository.RefreshTokenRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24 * 14; // 14일

    @Transactional
    public void saveOrUpdateRefreshToken(HttpServletResponse response, UserEntity user, String newRefreshToken) {
        // 1. DB에서 사용자의 리프레시 토큰을 조회
        Optional<RefreshTokenEntity> optionalToken = refreshTokenRepository.findByUser(user);

        if (optionalToken.isPresent()) {
            // 2-1. 토큰이 존재하면, 기존 토큰을 업데이트
            RefreshTokenEntity existingToken = optionalToken.get();
            existingToken.updateRefreshToken(newRefreshToken);
        } else {
            // 2-2. 토큰이 없으면, 새로 생성하여 저장
            RefreshTokenEntity newToken = new RefreshTokenEntity(user, newRefreshToken);
            refreshTokenRepository.save(newToken);
        }
        addRefreshTokenToCookie(response, newRefreshToken);
    }

    private void addRefreshTokenToCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refresh_token", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/"); // 모든 경로에서 쿠키 사용
        cookie.setMaxAge((int) (REFRESH_TOKEN_EXPIRE_TIME / 1000)); // 쿠키 유효기간 설정
        response.addCookie(cookie);
    }

    public RefreshTokenEntity findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 토큰입니다."));
    }
}
