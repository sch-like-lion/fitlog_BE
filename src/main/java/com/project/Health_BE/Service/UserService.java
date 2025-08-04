package com.project.Health_BE.Service;

import com.project.Health_BE.Dto.UserIdFindResponseDto;
import com.project.Health_BE.Entity.UserEntity;
import com.project.Health_BE.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserIdFindResponseDto findCustomIdByNickname(String nickname) {
        if (nickname == null || nickname.isEmpty()) {
            throw new IllegalArgumentException("닉네임은 필수 입력 사항입니다.");
        }
        Optional<UserEntity> user = userRepository.getUserByNickname(nickname);
        UserIdFindResponseDto responseDto = new UserIdFindResponseDto();
        responseDto.setCustom_id(user.get().getCustom_id());
        return responseDto;
    }

}
