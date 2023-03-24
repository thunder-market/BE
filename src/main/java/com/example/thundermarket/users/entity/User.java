package com.example.thundermarket.users.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private Long kakaoId;

    @Column(nullable = false, unique = true)
    private String email;

//    카카오 가입시 닉네임 처리 생각후 수정
    @Column
    private String nick;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String password, String email,String nick, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nick = nick;
        this.role = role;
    }

    public User(String username, Long kakaoId, String password, String email, UserRoleEnum role) {
        this.username = username;
        this.kakaoId = kakaoId;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }
}
