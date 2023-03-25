package com.example.thundermarket.users.controller;

import com.example.thundermarket.jwt.JwtUtil;
import com.example.thundermarket.products.dto.MessageResponseDto;
import com.example.thundermarket.users.dto.CheckEmailRequestDto;
import com.example.thundermarket.users.dto.CheckNickRequestDto;
import com.example.thundermarket.users.dto.LoginRequestDto;
import com.example.thundermarket.users.dto.SignupRequestDto;
import com.example.thundermarket.users.service.KakaoService;
import com.example.thundermarket.users.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserConroller {

    private final UserService userService;
    private final KakaoService kakaoService;

//   1. 회원 가입
    @PostMapping("/signup")
    public MessageResponseDto signup(@Valid @RequestBody SignupRequestDto dto){
        return userService.signup(dto);
    }
    //회원 가입시 이메일 체크
    @PostMapping("/signup/check-email")
    public MessageResponseDto signupEmailCheck(@Valid @RequestBody CheckEmailRequestDto dto){
        return userService.signupEmailCheck(dto);
    }
    //회원 가입시 닉네임 체크
    @PostMapping("/signup/check-nick")
    public MessageResponseDto signupEmailCheck(@Valid @RequestBody CheckNickRequestDto dto){
        return userService.signupNickCheck(dto);
    }

//    2. 로그인
    @PostMapping("/login")
    public MessageResponseDto login(@Valid @RequestBody LoginRequestDto dto, HttpServletResponse response){
        return userService.login(dto, response);
    }

//    3. 카카오
//    카카오 로그인 반환값 string redirect로 할지 어떻게 할지 좀 더 고민
    @GetMapping("/kakao/callback")
    public MessageResponseDto kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        // code: 카카오 서버로부터 받은 인가 코드
        String createToken = kakaoService.kakaoLogin(code, response);

        // Cookie 생성 및 직접 브라우저에 Set
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, createToken.substring(7));
        cookie.setPath("/");
        response.addCookie(cookie);

        return new MessageResponseDto(HttpStatus.OK,"카카오 로그인에 성공했습니다.") ;
    }
}
