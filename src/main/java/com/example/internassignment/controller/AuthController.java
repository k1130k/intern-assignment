package com.example.internassignment.controller;

import com.example.internassignment.dto.SigninRequest;
import com.example.internassignment.dto.SigninResponse;
import com.example.internassignment.dto.SignupRequest;
import com.example.internassignment.dto.SignupResponse;
import com.example.internassignment.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest signupRequest) {
        SignupResponse signupResponse = authService.signup(signupRequest);
        return ResponseEntity.ok(signupResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<SigninResponse> signin(@RequestBody SigninRequest signinRequest) {
        String token = authService.signin(signinRequest);
        return ResponseEntity.ok(new SigninResponse(token));
    }

    @PostMapping("/admin/signup")
    public ResponseEntity<SignupResponse> adminSignup(@RequestBody SignupRequest request) {
        SignupResponse response = authService.adminSignup(request);
        return ResponseEntity.ok(response);
    }
}
