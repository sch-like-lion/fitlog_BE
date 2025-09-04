package com.project.Health_BE.Security;

import com.project.Health_BE.Entity.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
public class CustomOAuth2User implements OAuth2User {

    private final UserEntity user;
    private final Map<String, Object> attributes;

    public CustomOAuth2User(UserEntity user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    public CustomOAuth2User(UserEntity user) {
        this.user = user;
        this.attributes = Collections.emptyMap();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().getKey()));
    }

    @Override
    public String getName() {
        return String.valueOf(user.getUserId());
    }
}
