package com.project.Health_BE.Repository;

import com.project.Health_BE.Entity.RefreshTokenEntity;
import com.project.Health_BE.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByRefreshToken(String refreshToken);
    Optional<RefreshTokenEntity> findByUser(UserEntity user);
}
