package com.example.internassignment.service;

import com.example.internassignment.config.JwtUtil;
import com.example.internassignment.dto.SigninRequest;
import com.example.internassignment.dto.SignupRequest;
import com.example.internassignment.dto.SignupResponse;
import com.example.internassignment.entity.User;
import com.example.internassignment.enums.UserRole;
import com.example.internassignment.global.exception.ClientException;
import com.example.internassignment.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public SignupResponse signup(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new ClientException("USER_ALREADY_EXISTS", "이미 가입된 사용자입니다.");
        }

        String encryptedPw = passwordEncoder.encode(signupRequest.getPassword());

        User newUser = new User(
                signupRequest.getUsername(),
                encryptedPw,
                signupRequest.getNickname(),
                UserRole.ROLE_USER
        );
        User savedUser = userRepository.save(newUser);

        return new SignupResponse(
                savedUser.getUsername(),
                savedUser.getNickname(),
                List.of(new SignupResponse.RoleDto(savedUser.getUserRole().getUserRole()))
        );
    }

    @Transactional
    public SignupResponse adminSignup(SignupRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ClientException("USER_ALREADY_EXISTS", "이미 가입된 사용자입니다.");
        }

        String encodedPw = passwordEncoder.encode(request.getPassword());

        User admin = new User(
                request.getUsername(),
                encodedPw,
                request.getNickname(),
                UserRole.ROLE_ADMIN
        );

        userRepository.save(admin);

        return new SignupResponse(
                admin.getUsername(),
                admin.getNickname(),
                List.of(new SignupResponse.RoleDto(admin.getUserRole().getUserRole()))
        );
    }

    @Transactional(readOnly = true)
    public String signin(SigninRequest signinRequest) {
        User user = userRepository.findByUsername(signinRequest.getUsername())
                .orElseThrow(() -> new ClientException("INVALID_CREDENTIALS", "아이디 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())) {
            throw new ClientException("INVALID_CREDENTIALS", "아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        return jwtUtil.createToken(user.getId(), user.getUsername(), user.getUserRole());
    }
}
