package com.example.internassignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SignupResponse {
    private String username;
    private String nickname;
    private List<RoleDto> roles;

    @Getter
    @AllArgsConstructor
    public static class RoleDto {
        private String role;
    }
}
