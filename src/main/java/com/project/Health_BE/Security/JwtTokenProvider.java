package com.project.Health_BE.Security;

import com.project.Health_BE.Entity.UserEntity;
import com.project.Health_BE.Repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long expiration = 1000L * 60 * 60 * 24; // 유효시간: 24시간(1일)

    private final Long accessTokenExpiration = 1000L * 60 * 60 ; //  유효시간: 1시간
    private final Long refreshExpiration = 1000L * 60 * 60 * 24 * 14; //  유효시간: 14일

    private final UserRepository userRepository; // UserRepository 주입

    public JwtTokenProvider(UserRepository userRepository) { // 생성자 수정
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 자동 생성 키
        this.userRepository = userRepository;
    }

    public String generateToken(String userEmail, Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(userEmail)      // 사용자 이메일
                .claim("UserId", userId)  // 유저id
                .setIssuedAt(now)           // 토큰 발행 시간
                .setExpiration(expiryDate)  // 토큰 만료 시간
                .signWith(key)              // 서명
                .compact();                 // 토큰 생성
    }
    //엑세스 토큰 생성
    public String generateAccessToken(String userEmail, Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpiration);

        return Jwts.builder()
                .setSubject(userEmail)
                .claim("userId", userId) // key를 소문자로 통일
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String userEmail, Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshExpiration);

        return Jwts.builder()
                .setSubject(userEmail)
                .claim("userId", userId) // key를 소문자로 통일
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    // JWT에서 이메일 추출
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // 이메일 반환
    }

    //JWT에서 UserId 추출
    public Long getUserIdFromToken(String token) {
        Claims claims= Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userId", Long.class);
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 토큰으로부터 Authentication 객체를 생성하는 메소드 추가
    public Authentication getAuthentication(String token) {
        Long userId = getUserIdFromToken(token);
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token user ID"));

        CustomOAuth2User customOAuth2User = new CustomOAuth2User(user);

        return new UsernamePasswordAuthenticationToken(customOAuth2User, "", customOAuth2User.getAuthorities());
    }
}
