package com.project.Health_BE.Config;

import com.project.Health_BE.Entity.UserEntity;
import com.project.Health_BE.Security.CustomOAuth2User;
import com.project.Health_BE.Security.JwtTokenProvider;
import com.project.Health_BE.Service.RefreshTokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Value("${app.oauth.redirect-uri}")
    private String redirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        UserEntity user = oAuth2User.getUser();

        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail(), user.getUserId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail(), user.getUserId());

        refreshTokenService.saveOrUpdateRefreshToken(response, user, refreshToken);

        String targetUrl = UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam("accessToken", accessToken)
                .build().toUriString();

        response.sendRedirect(targetUrl);
    }
}
