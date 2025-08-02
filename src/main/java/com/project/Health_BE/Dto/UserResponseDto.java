package com.project.Health_BE.Dto;
import lombok.*;

@Data
@NoArgsConstructor

public class UserResponseDto {

    private Long user_id;
    private String customId;
    private String nickname;
    private String email;
    private String profile_image_url;
    private String location;
    private String gender;
    private Integer height;
    private Integer weight;
    private String created_at;

    public  UserResponseDto(Long user_id, String customId, String nickname, String email, String profile_image_url,
                            String location, String gender, Integer height, Integer weight, String created_at) {
        this.user_id = user_id;
        this.customId = customId;
        this.nickname = nickname;
        this.email = email;
        this.profile_image_url = profile_image_url;
        this.location = location;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.created_at = created_at;
    }


}
