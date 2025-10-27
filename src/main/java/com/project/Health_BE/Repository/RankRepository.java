package com.project.Health_BE.Repository;

import com.project.Health_BE.Entity.RankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface RankRepository extends JpaRepository<RankEntity, Long> {

    @Query("SELECT rt FROM RankEntity rt WHERE rt.minScore <= :score ORDER BY rt.minScore DESC LIMIT 1")
    Optional<RankEntity> findTierByScore(@Param("score") int score);

    @Query("SELECT r FROM RankEntity r WHERE r.minScore > :currentMinScore ORDER BY r.minScore ASC LIMIT 1")
    Optional<RankEntity> findNextTier(@Param("currentMinScore") int currentMinScore);
}