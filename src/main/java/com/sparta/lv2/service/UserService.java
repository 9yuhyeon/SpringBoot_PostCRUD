package com.sparta.lv2.service;

import com.sparta.lv2.dto.LoginRequestDto;
import com.sparta.lv2.dto.SignupRequestDto;
import com.sparta.lv2.entity.User;
import com.sparta.lv2.entity.UserRoleEnum;
import com.sparta.lv2.jwt.JwtUtil;
import com.sparta.lv2.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final HttpServletResponse httpServletResponse;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, HttpServletResponse httpServletResponse) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.httpServletResponse = httpServletResponse;
    }

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public ResponseEntity<String> signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> findUser = userRepository.findByUsername(username);
        if (findUser.isPresent()) {
            throw new IllegalArgumentException("중복된 username 입니다.");
        }

        // 사용자 ROLE(권한) 확인
        UserRoleEnum role = UserRoleEnum.USER; // role 기본값 : USER

        if (requestDto.getAdminToken() != "") {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 관리자 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, password, role);
        userRepository.save(user);

        return new ResponseEntity<>("msg : 로그인 성공.", HttpStatus.OK);
    }

    public ResponseEntity<String> login(LoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(() ->
                new IllegalArgumentException("회원을 찾을 수 없습니다.")
        );

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("회원을 찾을 수 없습니다.");
        } else {
            String token = jwtUtil.createToken(user.getId(), user.getUsername());
            httpServletResponse.setHeader("Authorization", token);
            // ResponseDto에 msg, statusCode 반환
            return new ResponseEntity<>("msg : 로그인 성공.", HttpStatus.OK);
        }
    }
}