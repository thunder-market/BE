package com.example.thundermarket.users.repository;

import com.example.thundermarket.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
