package com.example.thundermarket.user.service;

import com.example.thundermarket.jwt.JwtUtil;
import com.example.thundermarket.user.dto.SignupRequestDto;
import com.example.thundermarket.user.entity.User;
import com.example.thundermarket.user.repository.UserRepository;
import com.example.thundermarket.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    @Transactional
    public void signup(SignupRequestDto dto){

        String userName = dto.getUserName();
        String password = encoder.encode(dto.getPassword());
        String email = dto.getEmail();
        String nick = dto.getNick();
        Optional<User> found = userRepository.findByUserName(userName);

        if(found.isPresent()){
            throw new RuntimeException("중복된 사용자가 존재합니다.");
        }

        UserRoleEnum role = UserRoleEnum.USER;

        if(isAdmin(dto)){
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(userName,password,email,nick, role);

        userRepository.save(user);
    }

    @Transactional
    public void login(SignupRequestDto dto, HttpServletResponse response){
        String username = dto.getUserName();

        User user = userRepository.findByUserName(username).orElseThrow(
                () -> new RuntimeException("등록된 사용자가 없습니다.")
        );

        String encodePassword = user.getPassword();

        if(!encoder.matches(dto.getPassword(), encodePassword)){

            throw new RuntimeException("인증 정보가 맞지 않아 실패했습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUserName(), user.getRole()));
    }

    private boolean isAdmin(SignupRequestDto dto){

        return dto.isAdmin() && dto.getAdminToken().equals(ADMIN_TOKEN);
    }
}
