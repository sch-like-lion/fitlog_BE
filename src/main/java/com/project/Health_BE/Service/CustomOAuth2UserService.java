package com.project.Health_BE.Service;

import com.project.Health_BE.Dto.OAuth2Attributes;
import com.project.Health_BE.Entity.UserEntity;
import com.project.Health_BE.Repository.UserRepository;
import com.project.Health_BE.Security.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1. 기본 OAuth2User 정보를 가져옵니다.
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 2. 서비스 제공자(google, kakao 등) ID와 사용자 이름 속성을 가져옵니다.
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // 3. 제공자별로 속성을 파싱하는 DTO를 사용합니다.
        OAuth2Attributes attributes = OAuth2Attributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // 4. 이메일로 기존 사용자인지, 신규 사용자인지 확인합니다.
        UserEntity user = userRepository.getUserByEmail(attributes.getEmail())
                .map(entity -> entity.updateProfile(attributes.getName(), null))
                .orElseGet(() -> userRepository.save(attributes.toEntity(registrationId)));

        // 5. CustomOAuth2User 객체를 생성하여 반환합니다.
        return new CustomOAuth2User(user, attributes.getAttributes());
    }
}