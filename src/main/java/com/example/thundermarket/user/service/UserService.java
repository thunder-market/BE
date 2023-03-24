package com.example.thundermarket.user.service;

import com.example.thundermarket.jwt.JwtUtil;
import com.example.thundermarket.user.dto.SignupRequestDto;
import com.example.thundermarket.user.entity.User;
import com.example.thundermarket.user.repository.UserRepository;
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

        String username = dto.getUsername();
        String password = encoder.encode(dto.getPassword());

        Optional<User> found = userRepository.findByUserId(username);

        if(found.isPresent()){
            throw new DuplicateUserException("중복된 사용자가 존재합니다.");
        }

        UserRoleEnum role = UserRoleEnum.USER;

        if(isAdmin(dto)){
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, role);

        userRepository.save(user);
    }

    @Transactional
    public void login(SignupRequestDto dto, HttpServletResponse response){
        String username = dto.getUsername();

        User user = userRepository.findByUserId(username).orElseThrow(
                () -> new NotFoundUserException("등록된 사용자가 없습니다.")
        );

        String encodePassword = user.getPassword();

        if(!encoder.matches(dto.getPassword(), encodePassword)){

            throw new NotAuthException("인증 정보가 맞지 않아 실패.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
    }

    private boolean isAdmin(SignupRequestDto dto){

        return dto.isAdmin() && dto.getAdminToken().equals(ADMIN_TOKEN);
    }
}
