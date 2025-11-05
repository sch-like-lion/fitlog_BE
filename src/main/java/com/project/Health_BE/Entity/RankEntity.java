package com.project.Health_BE.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RankEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String tierName;

    @Column(nullable = false)
    private Integer minScore; // 해당 티어를 달성하기 위한 최소 점수

    @Column(nullable = false)
    private Integer maxScore;

    @Column
    private String tierImageUrl; // 티어 이미지 URL
}