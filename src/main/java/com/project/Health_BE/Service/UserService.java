package com.project.Health_BE.Service;

import com.project.Health_BE.Dto.*;
import com.project.Health_BE.Entity.UserEntity;
import com.project.Health_BE.Exception.*;
import com.project.Health_BE.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final mailVerificationService mailVerificationService;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository, mailVerificationService mailVerificationService, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.mailVerificationService = mailVerificationService;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    public UserIdFindResponseDto findCustomIdByNickname(String nickname) {
        if (nickname == null || nickname.isEmpty()) {
            throw new IllegalArgumentException("닉네임은 필수 입력 사항입니다.");
        }
        Optional<UserEntity> user = userRepository.getUserByNickname(nickname);
        UserIdFindResponseDto responseDto = new UserIdFindResponseDto();
        responseDto.setCustom_id(user.get().getCustomId());
        return responseDto;
    }

    public SignupResponseDto Signup(SignupRequestDto requestDto) {
        if(userRepository.getUserByNickname(requestDto.getNickname()).isPresent()) {
            throw new DuplicateNicknameException(requestDto.getNickname());
        } else if (userRepository.getUserByEmail(requestDto.getEmail()).isPresent()) {
            throw new DuplicateEmailException(requestDto.getEmail());
        } else if (userRepository.getUserByCustomId(requestDto.getCustomId()).isPresent()) {
            throw new DuplicateCustomIdException(requestDto.getCustomId());
        }/// 닉네임, 메일, 아이디 중복 예외 처리'

        String encrypted;
        try{
            EncryptionService sha256 = new EncryptionService();
            encrypted = sha256.encrypt(requestDto.getPassword());
            /// PW 암호화
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }


        UserEntity entity = UserEntity.builder()
                .nickname(requestDto.getNickname())
                .custom_Id(requestDto.getCustomId())
                .email(requestDto.getEmail())
                .password(encrypted)
                .height(requestDto.getHeight())
                .weight(requestDto.getWeight())
                .build();
        userRepository.save(entity);
        /// build후 DB에 위임한 Id, created_at, updated_at 가져오기 위한 save

        SignupResponseDto responseDto = new SignupResponseDto();
        responseDto.DtofromEntity(entity);
        return responseDto;
    }

    }
}
