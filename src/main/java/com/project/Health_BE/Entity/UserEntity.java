package com.project.Health_BE.Entity;

import com.project.Health_BE.Dto.SignupResponseDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="users")
@Getter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "user_id", unique = true)
    private Long userId;

    @Column(name = "customId", nullable = false, unique = true)
    private String customId;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "profile_image_url")
    private String profile_image_url;

    @Column(name = "location")
    private String location;

    @Column(name = "gender")
    private String gender;

    @Column(name = "height")
    private Integer height;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @Builder
    private UserEntity(String custom_Id, String password, String nickname,
                       String email, String profile_image_url, String location,
                       String gender, Integer height, Integer weight) {
        this.customId = custom_Id;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.profile_image_url = profile_image_url;
        this.location = location;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }

    public static UserEntity createUser(String nickname, String email) {
        return UserEntity.builder()
                .nickname(nickname)
                .email(email)
                .build();
    }
    public static UserEntity createOAuthUser(String nickname, String email, String providerId){
        return UserEntity.builder()
                .nickname(nickname)
                .email(email)
                .custom_Id(providerId)
                .build();
    }

    public UserEntity updateProfile(String nickname, String profile_image_url) {
        this.nickname = nickname;
        this.profile_image_url = profile_image_url;
        this.updated_at = LocalDateTime.now();
        return this;
    }

    @PrePersist
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_at = LocalDateTime.now();
    }
}
