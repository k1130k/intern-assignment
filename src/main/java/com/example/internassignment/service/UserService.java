package com.example.internassignment.service;

import com.example.internassignment.dto.SignupResponse;
import com.example.internassignment.entity.User;
import com.example.internassignment.enums.UserRole;
import com.example.internassignment.global.exception.ClientException;
import com.example.internassignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public SignupResponse promoteToAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ClientException("USER_NOT_FOUND", "해당 사용자를 찾을 수 없습니다."));

        user.promoteToAdmin();

        return new SignupResponse(
                user.getUsername(),
                user.getNickname(),
                List.of(new SignupResponse.RoleDto("Admin"))
        );
    }
}

