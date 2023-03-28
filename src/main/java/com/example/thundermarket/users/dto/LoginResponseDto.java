package com.example.thundermarket.users.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String nick;

    public LoginResponseDto(String nick) {
        this.nick = nick;
    }
}
