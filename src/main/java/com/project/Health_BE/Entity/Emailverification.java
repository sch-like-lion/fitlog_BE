package com.project.Health_BE.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="Emailverification")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Emailverification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email")
    private String email;
    private String code;
    private LocalDateTime updatetime;
    private boolean verify;
}
