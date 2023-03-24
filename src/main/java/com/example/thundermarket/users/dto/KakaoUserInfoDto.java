package com.example.thundermarket.users.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfoDto {
    private Long id;
    private String email;
    private String nick;

    public KakaoUserInfoDto(Long id, String nick, String email) {
        this.id = id;
        this.nick = nick;
        this.email = email;
    }
}