package com.example.thundermarket.user.controller;

import com.example.thundermarket.user.dto.SignupRequestDto;
import com.example.thundermarket.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserConroller {

    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<Object> signup(SignupRequestDto dto){

        userService.signup(dto);

        return ResponseEntity.ok("회원가입 성공");
    }
    @PostMapping("/auth/login")
    public ResponseEntity<Object> login(SignupRequestDto dto, HttpServletResponse response){

        userService.login(dto, response);

        return ResponseEntity.ok("로그인 성공");

    }
}
