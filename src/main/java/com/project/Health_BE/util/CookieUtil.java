package com.project.Health_BE.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Base64;

public class CookieUtil {

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            return;
        }

        for (Cookie cookie : cookies){
            if(name.equals(cookie.getName())){
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }


    // 객체를 JSON 문자열로 변환
    public static String serialize(Object obj) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(obj);
        return Base64.getUrlEncoder().encodeToString(jsonString.getBytes());
    }

    // JSON 문자열을 객체로 변환
    public static <T> T deserialize(Cookie cookie, Class<T> cls) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = new String(Base64.getUrlDecoder().decode(cookie.getValue()));
        return objectMapper.readValue(jsonString, cls);
    }
}