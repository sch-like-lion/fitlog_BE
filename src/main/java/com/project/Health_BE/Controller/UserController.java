package com.project.Health_BE.Controller;

import com.project.Health_BE.Dto.*;
import com.project.Health_BE.Exception.DuplicateCustomIdException;
import com.project.Health_BE.Exception.DuplicateEmailException;
import com.project.Health_BE.Exception.DuplicateNicknameException;
import com.project.Health_BE.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private UserController(UserService userService) {
        this.userService=userService;
    }

    @PostMapping("find-id")
    public ResponseEntity<?> getCustomIdByNickname(@RequestBody String nickname, boolean emailResult) {
        if (emailResult) {
            try {
                UserIdFindResponseDto userDto = userService.findCustomIdByNickname(nickname);
                return ResponseEntity.ok(userDto);
            } catch (IllegalArgumentException e) {
                Map<String, String> errorResponse = Collections.singletonMap("message", "필수 입력 항목이 누락되었거나 유효하지 않습니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            } catch (RuntimeException e) {
                Map<String, String> errorResponse = Collections.singletonMap("message", "서버에 접속할 수 없습니다.");
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse);
            } catch (Exception e) {
                Map<String, String> errorResponse = Collections.singletonMap("message", "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
        }

        Map<String, String> errorResponse = Collections.singletonMap("message", "이메일 인증이 되지 않았습니다.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @PostMapping("signup")
    public ResponseEntity<?> userSignup(@RequestBody SignupRequestDto requestDto){
        try {
            SignupResponseDto responseDto = userService.Signup(requestDto);
            return ResponseEntity.ok(responseDto);
        } catch (DuplicateNicknameException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (DuplicateCustomIdException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (DuplicateEmailException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // 비밀번호 찾기(재설정)
    @PutMapping("reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetDto dto) {
        try {
            userService.resetPasswordWithSHA256(dto.getEmail(), dto.getAuthCode(), dto.getNewPassword());
            return ResponseEntity.ok(Collections.singletonMap("message", "비밀번호가 성공적으로 재설정되었습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "서버 오류가 발생했습니다."));
        }
    }

    // 일반 로그인
    @PostMapping("login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        LoginResponseDto response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
