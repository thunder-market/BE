package com.example.thundermarket.user.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter

public class SignupRequestDto {

        @NotBlank(message = "userId를 입력해주세요.")
        @Size(min = 4, max = 10)
        @Pattern(regexp = "[a-z0-9]+", message = "영어 소문자랑 숫자를 이용해 4-10자 아이디를 입력해주세요.")
        private  String username;

        @NotBlank(message = "password를 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+])[a-zA-Z\\d!@#$%^&*()_+]{8,15}$", message = "숫자와 영어 소문자와 특수문자를 사용해 8-15자리 비밀번호를 입력해주세요.")
        private String password;

        private boolean admin = false;
        private String adminToken ="";

}
