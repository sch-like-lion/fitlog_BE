package com.project.Health_BE.Security;


import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long expiration = 1000L * 60 * 60 * 24; // 유효시간: 24시간(1일)


        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()

                .setIssuedAt(now)           // 토큰 발행 시간
                .setExpiration(expiryDate)  // 토큰 만료 시간
                .signWith(key)              // 서명
                .compact();                 // 토큰 생성
    }

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()

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

}
