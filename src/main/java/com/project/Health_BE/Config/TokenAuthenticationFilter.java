package com.project.Health_BE.Config;

import com.project.Health_BE.Security.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    private final static String HEADER_AUTHORIZATION="Authorization";
    private final static String BEARER_PREFIX="Bearer ";

    @Override
    //authorization code 헤더를 가져와 인증 정보를 저장함과 동시에 인증되었음을 나타낼 수 있는 SecurityContextHolder 저장
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader= request.getHeader(HEADER_AUTHORIZATION);
        String token = getAccessToken(authorizationHeader);

        // token이 null이 아니고, 유효한 경우에만 인증 정보를 설정
        if(token != null && jwtTokenProvider.validateToken(token)){
            Authentication authentication=jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        
        filterChain.doFilter(request, response);
    }

    private String getAccessToken(String authorizationHeader) {
        //헤더가 없거나 Bearer로 시작하지 않으면 null 반환
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            return authorizationHeader.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}
