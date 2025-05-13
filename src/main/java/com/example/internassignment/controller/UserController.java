package com.example.internassignment.controller;

import com.example.internassignment.dto.SignupResponse;
import com.example.internassignment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;

    @PatchMapping("/{userId}/roles")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<SignupResponse> promoteToAdmin(@PathVariable Long userId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(">>> 현재 인증 정보: " + auth);
        System.out.println(">>> 권한 목록: " + auth.getAuthorities());

        SignupResponse response = userService.promoteToAdmin(userId);
        return ResponseEntity.ok(response);
    }
}
