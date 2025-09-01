package com.project.Health_BE.Controller;

import com.project.Health_BE.Dto.CreateAccessTokenRequestDto;
import com.project.Health_BE.Dto.CreateAccessTokenResponseDto;
import com.project.Health_BE.Service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RestController
public class TokenApiController {
    private final TokenService tokenService;

    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponseDto> createNewAccessToken(@RequestBody CreateAccessTokenRequestDto request){
        String newAccessToken= tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateAccessTokenResponseDto(newAccessToken));
    }
    @GetMapping("/oauth-success")
    public ResponseEntity<String> oauthSuccess(@RequestParam(name = "accessToken", required = false) String accessToken) {
        if (accessToken == null || accessToken.isEmpty()) {
            return ResponseEntity.ok("Login success. No accessToken provided.");
        }
        return ResponseEntity.ok("accessToken=" + accessToken);
    }

}
