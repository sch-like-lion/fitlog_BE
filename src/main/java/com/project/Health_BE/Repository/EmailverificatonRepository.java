package com.project.Health_BE.Repository;

import com.project.Health_BE.Entity.Emailverification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailverificatonRepository extends JpaRepository<Emailverification, String> {
    Optional<Emailverification> findByEmail(String email);
}