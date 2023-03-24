package com.example.thundermarket.users.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignupRequestDto {
//        회원가입시 정규식은 추후 프론트와 상의후 결정

        @NotBlank(message = "아이디를 입력해주세요.")
        @Size(min = 4, max = 10)
        @Pattern(regexp = "[a-z0-9]+", message = "영어 소문자랑 숫자를 이용해 4-10자 아이디를 입력해주세요.")
        private  String username;

//        닉네임 글자수도 정해야 한다.
        @NotBlank(message = "닉네임을 입력해주세요.")
        private String nick;
//        이메일 양식 추가
        @NotBlank(message = "이메일을 입력해주세요.")
        private String email;

        @NotBlank(message = "패스워드를 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+])[a-zA-Z\\d!@#$%^&*()_+]{8,15}$", message = "숫자와 영어 소문자와 특수문자를 사용해 8-15자리 비밀번호를 입력해주세요.")
        private String password;

        private boolean admin = false;
        private String adminToken ="";

}
