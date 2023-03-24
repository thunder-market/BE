package com.example.thundermarket.security;

import com.example.thundermarket.user.entity.User;
import com.example.thundermarket.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl {

    private final UserRepository userRepository;

//    userRepository에
    public UserDetails loadUsersByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username).orElseThrow(
                () -> new UsernameNotFoundException("유저를 찾을 수 없습니다.")
        );
        return new UserDetailsImpl(user, user.getUserName());


    }

}
