package com.project.Health_BE.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/// 암호화 sha256 방법
public class EncryptionService {
    public String encrypt(String text){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());
            return bytesToHex(md.digest());
        } catch(NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 알고리즘이 지원되지 않습니다.", e);
        }

    }
    public String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
