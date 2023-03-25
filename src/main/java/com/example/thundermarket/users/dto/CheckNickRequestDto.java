package com.example.thundermarket.users.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class CheckNickRequestDto{
        @NotBlank(message = "닉네임을 입력해주세요.")
        @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,15}$", message = "닉네임은 2~15글자, 한글, 알파벳, 숫자를 입력하셔야합니다.")
        private String nick;
}
