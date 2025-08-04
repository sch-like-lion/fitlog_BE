package com.project.Health_BE.Exception;

public class DuplicateCustomIdException extends RuntimeException {
    public DuplicateCustomIdException(String CustomId) {
        super("이미 사용중인 아이디 입니다: " + CustomId);
    }
}
