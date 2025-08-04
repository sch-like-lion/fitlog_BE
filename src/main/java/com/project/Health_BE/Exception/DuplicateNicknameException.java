package com.project.Health_BE.Exception;

public class DuplicateNicknameException extends RuntimeException {
    public DuplicateNicknameException(String nickname) {
        super("이미 사용 중인 닉네임 입니다: " + nickname);
    }
}
