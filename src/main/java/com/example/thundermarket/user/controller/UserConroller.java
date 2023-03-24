package com.example.thundermarket.user.controller;

import com.example.thundermarket.jwt.JwtUtil;
import com.example.thundermarket.user.dto.SignupRequestDto;
import com.example.thundermarket.user.service.KakaoService;
import com.example.thundermarket.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class UserConroller {

    private final UserService userService;
    private final KakaoService kakaoService;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(SignupRequestDto dto){

        userService.signup(dto);

        return ResponseEntity.ok("회원가입 성공");
    }
    @PostMapping("/login")
    public ResponseEntity<Object> login(SignupRequestDto dto, HttpServletResponse response){

        userService.login(dto, response);

        return ResponseEntity.ok("로그인 성공");

    }
    @GetMapping("/kakao/callback")
    public ResponseEntity<Object> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        // code: 카카오 서버로부터 받은 인가 코드
        String createToken = kakaoService.kakaoLogin(code, response);

        // Cookie 생성 및 직접 브라우저에 Set
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, createToken.substring(7));
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok("로그인 성공");
    }
}
