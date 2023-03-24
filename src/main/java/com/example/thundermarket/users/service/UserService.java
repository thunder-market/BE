package com.example.thundermarket.users.service;

import com.example.thundermarket.jwt.JwtUtil;
import com.example.thundermarket.products.dto.MessageResponseDto;
import com.example.thundermarket.users.dto.LoginRequestDto;
import com.example.thundermarket.users.dto.SignupRequestDto;
import com.example.thundermarket.users.entity.User;
import com.example.thundermarket.users.repository.UserRepository;
import com.example.thundermarket.users.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

//    회원가입
    @Transactional
    public MessageResponseDto signup(SignupRequestDto dto){

        String username = dto.getUsername();
        String password = encoder.encode(dto.getPassword());
        String email = dto.getEmail();
        String nick = dto.getNick();

//        Optional 로 받는걸 추후 수정?
        Optional<User> found = userRepository.findByusername(username);
        if(found.isPresent()){
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        Optional<User> foundEmail = userRepository.findByEmail(email);

        if(foundEmail.isPresent()){
            throw new IllegalArgumentException("중복된 이메일이 존재합니다");
        }

        UserRoleEnum role = UserRoleEnum.USER;

        if(dto.isAdmin()){
            if (!dto.getAdminToken().equals(ADMIN_TOKEN)){
                throw new IllegalArgumentException("관리자 암호가 틀립니다");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username,password,email,nick, role);
        userRepository.saveAndFlush(user);
        return new MessageResponseDto(HttpStatus.OK, "회원가입이 완료되었습니다.");
    }

//    로그인
    @Transactional
    public MessageResponseDto login(LoginRequestDto dto, HttpServletResponse response){
        String username = dto.getUsername();

        User user = userRepository.findByusername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        String encodePassword = user.getPassword();

        if(!encoder.matches(dto.getPassword(), encodePassword)){
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return new MessageResponseDto(HttpStatus.OK, "로그인이 완료되었습니다.");
    }

}
