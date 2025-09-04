package com.project.Health_BE.Config;

import com.project.Health_BE.Security.JwtTokenProvider;
import com.project.Health_BE.Service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> auth
                        //requestmatcher에 등록된 api는 인증 없이 접근 가능 -> 인증 없이 접근 할 수 있어야 하는 페이지는 추가해주세요
                        .requestMatchers("/api/users/signup", "/api/users/login", "/api/token", "/login/**", "/oauth2/**", "/oauth-success","/oauth2/authorization/**").permitAll()
                        .anyRequest().authenticated()
                ).oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authorization -> authorization
                                .authorizationRequestRepository(new HttpCookieOAuth2AuthorizationRequestRepository()))
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .successHandler(oAuth2LoginSuccessHandler))
                .addFilterBefore(new TokenAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}