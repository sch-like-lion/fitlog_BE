package com.project.Health_BE.Entity;

public enum ActivityType {
    // 여기에 점수를 부여할 활동의 종류를 모두 정의합니다.
    CREATE_NEW_POST,    // 새 게시글 작성
    ADD_COMMENT,        // 댓글 작성
    RECEIVE_LIKE,       // '좋아요' 받음
    DAILY_LOGIN         // 일일 로그인
}