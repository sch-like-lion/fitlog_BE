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
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuth2Attributes attributes = OAuth2Attributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        UserEntity user = userRepository.getUserByEmail(attributes.getEmail())
                .map(entity -> entity.updateProfile(attributes.getName(), null))
                .orElseGet(() -> userRepository.save(attributes.toEntity(registrationId)));

        return new CustomOAuth2User(user, attributes.getAttributes());
    }
}