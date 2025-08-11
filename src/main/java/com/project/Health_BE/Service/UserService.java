package com.project.Health_BE.Service;

import com.project.Health_BE.Dto.*;
import com.project.Health_BE.Entity.UserEntity;
import com.project.Health_BE.Exception.*;
import com.project.Health_BE.Repository.UserRepository;
import com.project.Health_BE.Security.JwtTokenProvider;
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

    // 비밀번호 찾기(초기화)
    public void resetPasswordWithSHA256(String email, String authCode, String newPassword) {
        mailVerificationDto resultDto = mailVerificationService.Verification(authCode);
        if (!resultDto.isMailcheck()) {
            throw new IllegalArgumentException("인증 코드가 유효하지 않습니다.");
        }

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일로 등록된 사용자가 없습니다."));

        try {
            EncryptionService sha256 = new EncryptionService();
            String encrypted = sha256.encrypt(newPassword);
            user.updatePassword(encrypted);
            userRepository.save(user);
        } catch (RuntimeException e) {
            throw new RuntimeException("비밀번호 암호화 중 오류 발생", e);
        }
    }

    // 일반 로그인
    public LoginResponseDto login(LoginRequestDto request) {
        String customId = request.getCustomId();
        String rawPassword = request.getPassword();

        UserEntity user = userRepository.findByCustomId(customId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));

        EncryptionService sha256 = new EncryptionService();
        String encryptedPassword = sha256.encrypt(rawPassword);

        if (!user.getPassword().equals(encryptedPassword)) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.generateToken(user.getCustomId());

        return new LoginResponseDto("로그인 성공!", token);
    }
}
