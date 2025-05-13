package com.example.internassignment.controller;

import com.example.internassignment.dto.SigninRequest;
import com.example.internassignment.dto.SigninResponse;
import com.example.internassignment.dto.SignupRequest;
import com.example.internassignment.dto.SignupResponse;
import com.example.internassignment.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "인증 API", description = "회원가입, 로그인 관련 API")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
    @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = SignupResponse.class)))
    @ApiResponse(responseCode = "400", description = "회원가입 실패 (이미 가입된 사용자)", content = @Content(schema = @Schema(example = "{\"error\": {\"code\": \"USER_ALREADY_EXISTS\", \"message\": \"이미 가입된 사용자입니다.\"}}")))
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest signupRequest) {
        SignupResponse signupResponse = authService.signup(signupRequest);
        return ResponseEntity.ok(signupResponse);
    }

    @Operation(summary = "로그인", description = "등록된 사용자로 로그인하고 JWT 토큰을 발급합니다.")
    @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = SigninResponse.class)))
    @ApiResponse(responseCode = "400", description = "로그인 실패 (잘못된 계정 정보)", content = @Content(schema = @Schema(example = "{\"error\": {\"code\": \"INVALID_CREDENTIALS\", \"message\": \"아이디 또는 비밀번호가 올바르지 않습니다.\"}}")))
    @PostMapping("/login")
    public ResponseEntity<SigninResponse> signin(@RequestBody SigninRequest signinRequest) {
        String token = authService.signin(signinRequest);
        return ResponseEntity.ok(new SigninResponse(token));
    }

    @Operation(summary = "관리자 회원가입", description = "새로운 관리자를 등록합니다. (관리자 권한 필요)")
    @ApiResponse(responseCode = "200", description = "관리자 회원가입 성공", content = @Content(schema = @Schema(implementation = SignupResponse.class)))
    @ApiResponse(responseCode = "400", description = "회원가입 실패 (이미 가입된 사용자)", content = @Content(schema = @Schema(example = "{\"error\": {\"code\": \"USER_ALREADY_EXISTS\", \"message\": \"이미 가입된 사용자입니다.\"}}")))
    @PostMapping("/admin/signup")
    public ResponseEntity<SignupResponse> adminSignup(@RequestBody SignupRequest request) {
        SignupResponse response = authService.adminSignup(request);
        return ResponseEntity.ok(response);
    }
}
