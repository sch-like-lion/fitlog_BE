package com.project.Health_BE.Controller;

import com.project.Health_BE.Dto.UserRankInfoResponseDto;
import com.project.Health_BE.Entity.UserEntity;
import com.project.Health_BE.Service.RankService;
import com.project.Health_BE.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rank")
@AllArgsConstructor
public class RankController {
    private final UserService userService;
    private final RankService rankService;

    @GetMapping("")
    public ResponseEntity<UserRankInfoResponseDto> getUserRank(@RequestHeader("Authorization") String authorization) {
        UserRankInfoResponseDto rankInfo = rankService.getUserRankInfo(authorization);
        return ResponseEntity.ok(rankInfo);
    }

    @GetMapping("overall")
    public ResponseEntity<UserRankInfoResponseDto> getUserOverAllRank(@RequestHeader("Authorization") String authorization) {
        UserRankInfoResponseDto rankInfo = rankService.getUserRankInfo(authorization);
        return ResponseEntity.ok(rankInfo);
    }
}
