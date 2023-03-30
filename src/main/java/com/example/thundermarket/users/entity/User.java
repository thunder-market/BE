package com.example.thundermarket.users.entity;

import com.example.thundermarket.products.entity.ProductDibs;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private Long kakaoId;

    @Column(nullable = false, unique = true)
    private String email;

//    카카오 가입시 닉네임 처리 생각후 수정
    @Column(nullable = false, unique = true)
    private String nick;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany
    @JoinColumn(name = "userId")
    List<ProductDibs> productDibsList = new ArrayList<>();

    public User(String email,String password,String nick, UserRoleEnum role) {
        this.email = email;
        this.password = password;
        this.nick = nick;
        this.role = role;
    }

    public User(Long kakaoId,String nick, String password, String email, UserRoleEnum role) {
        this.kakaoId = kakaoId;
        this.nick = nick;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }
}
