package com.example.thundermarket.user.repository;

import com.example.thundermarket.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByKakaoId(Long id);
    Optional<User> findByEmail(String email);
}
