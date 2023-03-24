package com.example.thundermarket.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfoDto {
    private Long id;
    private String email;
    private String nicknmae;

    public KakaoUserInfoDto(Long id, String nickname, String email) {
        this.id = id;
        this.nicknmae = nickname;
        this.email = email;
    }
}