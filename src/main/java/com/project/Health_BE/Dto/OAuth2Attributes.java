package com.project.Health_BE.Dto;

import com.project.Health_BE.Entity.UserEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
public class OAuth2Attributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private String providerId;

    @Builder
    public OAuth2Attributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture, String providerId) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.providerId = providerId;
    }

    public static OAuth2Attributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("kakao".equals(registrationId)) {
            return ofKakao("id", attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }
    //구글 사용자 가져오기
    private static OAuth2Attributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        String email = (String) attributes.get("email");
        String providerId = (String) attributes.get(userNameAttributeName);
        if (email==null){
            email="google_"+providerId+"@gmail.com";
        }
        return OAuth2Attributes.builder()
                .name((String) attributes.get("name"))
                .email(email)
                .providerId(providerId)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
    //카카오 사용자 가져오기
    private static OAuth2Attributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = kakaoAccount != null ? (Map<String, Object>) kakaoAccount.get("profile") : null;

        String providerId = String.valueOf(attributes.get(userNameAttributeName));
        String nickname = profile != null ? (String) profile.get("nickname") : null;
        String email = kakaoAccount != null ? (String) kakaoAccount.get("email") : null;
        if (email == null) {
            email = "kakao_" + providerId + "@example.com"; // fallback for local test
        }

        return OAuth2Attributes.builder()
                .name(nickname)
                .email(email)
                .providerId(providerId)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public UserEntity toEntity(String provider) {
        return UserEntity.builder()
                .nickname(name)
                .email(email)
                .profile_image_url(null)
                .custom_Id(provider + "_" + providerId)
                .password(UUID.randomUUID().toString())
                .build();
    }
}