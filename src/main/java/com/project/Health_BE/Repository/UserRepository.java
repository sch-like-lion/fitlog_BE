package com.project.Health_BE.Repository;

import com.project.Health_BE.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long>{
    Optional<UserEntity> getUserByNickname(String nickname) ;
    Optional<UserEntity> getUserByEmail(String email);
    Optional<UserEntity> getUserByCustomId(String customId);
}
