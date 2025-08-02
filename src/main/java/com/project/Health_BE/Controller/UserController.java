package com.project.Health_BE.Controller;

import com.project.Health_BE.Dto.UserIdFindResponseDto;
import com.project.Health_BE.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
