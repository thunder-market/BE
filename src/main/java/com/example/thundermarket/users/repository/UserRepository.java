package com.example.thundermarket.users.repository;

import com.example.thundermarket.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByusername(String username);
    Optional<User> findByKakaoId(Long id);
    Optional<User> findByEmail(String email);
}
