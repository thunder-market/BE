package com.example.thundermarket.security;

import com.example.thundermarket.users.entity.User;
import com.example.thundermarket.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl {

    private final UserRepository userRepository;
    
    public UserDetails loadUsersByusername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("유저를 찾을 수 없습니다.")
        );
        return new UserDetailsImpl(user, user.getEmail());

    }

}
