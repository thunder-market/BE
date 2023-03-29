package com.example.thundermarket.users.controller;

import com.example.thundermarket.jwt.JwtUtil;
import com.example.thundermarket.products.dto.MessageResponseDto;
import com.example.thundermarket.users.dto.*;
import com.example.thundermarket.users.service.KakaoService;
import com.example.thundermarket.users.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    // 1. 회원 가입
    @PostMapping("/signup")
    public MessageResponseDto signup(@Valid @RequestBody SignupRequestDto dto){
        return userService.signup(dto);
    }

    // 1-1. 회원 가입시 이메일 체크
    @PostMapping("/signup/check-email")
    public MessageResponseDto signupEmailCheck(@Valid @RequestBody CheckEmailRequestDto dto){
        return userService.signupEmailCheck(dto);
    }

    // 1-2. 회원 가입시 닉네임 체크
    @PostMapping("/signup/check-nick")
    public MessageResponseDto signupEmailCheck(@Valid @RequestBody CheckNickRequestDto dto){
        return userService.signupNickCheck(dto);
    }

//    2. 로그인
    @PostMapping("/login")
    public LoginResponseDto login(@Valid @RequestBody LoginRequestDto dto, HttpServletResponse response){
        return userService.login(dto, response);
    }

}
