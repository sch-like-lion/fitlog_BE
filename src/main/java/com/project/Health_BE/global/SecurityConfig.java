package com.project.Health_BE.global;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());
        return http.build();
    }
    /// 나중에 로그인 필요하고 JWT가 잘 되어있으면 아래 코드 사용
    /// http
    ///     .csrf(csrf -> csrf.disable())
    ///     .authorizeHttpRequests(auth -> auth
    ///         .requestMatchers("/api/auth/**").permitAll()   // 회원가입, 로그인은 열어둠
    ///         .anyRequest().authenticated()                  // 나머지는 인증 필요
    ///     )
    ///     .formLogin(form -> form.disable())
    ///     .httpBasic(basic -> basic.disable());
    ///     return http.build();
}
