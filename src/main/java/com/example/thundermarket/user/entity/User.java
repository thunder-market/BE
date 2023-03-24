package com.example.thundermarket.user.entity;

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
    private String userName;

    @Column(nullable = false)
    private String password;

    private Long kakaoId;

    @Column(nullable = false, unique = true)
    private String email;
    @Column
    private String nick;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String userName, String password, String email,String nick, UserRoleEnum role) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.nick = nick;
        this.role = role;
    }

    public User(String userName, Long kakaoId, String password, String email, UserRoleEnum role) {
        this.userName = userName;
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
