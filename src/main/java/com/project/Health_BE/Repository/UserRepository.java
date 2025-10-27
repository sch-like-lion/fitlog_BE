package com.project.Health_BE.Repository;

import com.project.Health_BE.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long>{
    Optional<UserEntity> getUserByNickname(String nickname) ;
    Optional<UserEntity> getUserByEmail(String email);
    Optional<UserEntity> getUserByCustomId(String customId);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByEmailAndPassword(String email, String password);
    Optional<UserEntity> findByCustomId(String customId);

    @Query(
            value = "SELECT ranking FROM ("+
                    "SELECT user_id, RANK() OVER (ORDER BY total_point DESC) AS ranking )"+
                    " FROM users" +
                    ") AS ranked_users WHERE user_id = :userId",
            nativeQuery = true
    )
    Long findOverallRankByUserId(@Param("userId") Long userId);

    @Query(
            value = "SELECT ranking FROM ("+
                    "SELECT user_id, RANK() OVER (ORDER BY total_point DESC) AS ranking FROM users "+
                    "WHERE total_point BETWEEN :minPoint AND :maxPoint"+
                    ") AS tiered_users WHERE user_id = :userId",
            nativeQuery = true
    )
    Long findRankInTier(@Param("userId") Long userId, @Param("minPoint") int minPoint, @Param("maxPoiSnt") int maxPoint);

}
