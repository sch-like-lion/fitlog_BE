package com.project.Health_BE.Exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email) {
        super("이미 사용 중인 이메일 입니다: " + email);
    }
}
