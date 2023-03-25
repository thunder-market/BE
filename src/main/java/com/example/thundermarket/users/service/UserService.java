package com.example.thundermarket.users.service;

import com.example.thundermarket.jwt.JwtUtil;
import com.example.thundermarket.products.dto.MessageResponseDto;
import com.example.thundermarket.users.dto.CheckEmailRequestDto;
import com.example.thundermarket.users.dto.CheckNickRequestDto;
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

        String email = dto.getEmail();
        String password = encoder.encode(dto.getPassword());
        String nick = dto.getNick();

//        이메일 닉네임 중복체크는 추후 프론트와 협의후 삭제 or 유지
        boolean isEmailExist = userRepository.existsByEmail(dto.getEmail());
        if (isEmailExist) throw new IllegalArgumentException("이미 가입된 이메일입니다.");

        boolean isNickExist = userRepository.existsByNick(dto.getNick());
        if (isNickExist) throw new IllegalArgumentException("이미 가입된 닉네임입니다.");


        UserRoleEnum role = UserRoleEnum.USER;
        if(dto.isAdmin()){
            if (!dto.getAdminToken().equals(ADMIN_TOKEN)){
                throw new IllegalArgumentException("관리자 암호가 틀립니다");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(email,password,nick, role);
        userRepository.saveAndFlush(user);
        return new MessageResponseDto(HttpStatus.OK, "회원가입이 완료되었습니다.");
    }

//    로그인
    @Transactional
    public MessageResponseDto login(LoginRequestDto dto, HttpServletResponse response){
        String email = dto.getEmail();

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        String encodePassword = user.getPassword();

        if(!encoder.matches(dto.getPassword(), encodePassword)){
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getEmail(), user.getRole()));
        return new MessageResponseDto(HttpStatus.OK, "로그인이 완료되었습니다.");
    }

//    이메일 중복 체크. 이메일이 있으면 true - 중복된 이메일 반환 / 이메일이 없으면 false 사용가능한 이메일
    public MessageResponseDto signupEmailCheck(CheckEmailRequestDto dto) {
        boolean isEmailExist = userRepository.existsByEmail(dto.getEmail());
        return isEmailExist
                ? new MessageResponseDto(HttpStatus.BAD_REQUEST, "중복된 이메일입니다.")
                : new MessageResponseDto(HttpStatus.OK, "사용가능한 이메일입니다.");
    }

//    닉네임 중복 체크. 닉네임이 있으면 true - 중복된 닉네임 반환 / 닉네임이 없으면 false 사용가능한 닉네임
    public MessageResponseDto signupNickCheck(CheckNickRequestDto dto) {
        boolean isNickExist = userRepository.existsByNick(dto.getNick());
        return isNickExist
                ? new MessageResponseDto(HttpStatus.BAD_REQUEST, "중복된 닉네임입니다.")
                : new MessageResponseDto(HttpStatus.OK, "사용가능한 닉네임입니다.");
    }
}
