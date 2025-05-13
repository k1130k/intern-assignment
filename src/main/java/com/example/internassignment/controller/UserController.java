package com.example.internassignment.controller;

import com.example.internassignment.dto.SignupResponse;
import com.example.internassignment.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "사용자 관리 API", description = "사용자 역할 관리 API (관리자 권한 필요)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "사용자 관리자 권한 부여", description = "특정 사용자에게 관리자 권한을 부여합니다.", security = { @SecurityRequirement(name = "bearerAuth") })
    @ApiResponse(responseCode = "200", description = "관리자 권한 부여 성공", content = @Content(schema = @Schema(implementation = SignupResponse.class)))
    @ApiResponse(responseCode = "400", description = "사용자를 찾을 수 없음", content = @Content(schema = @Schema(example = "{\"error\": {\"code\": \"USER_NOT_FOUND\", \"message\": \"해당 사용자를 찾을 수 없습니다.\"}}")))
    @ApiResponse(responseCode = "403", description = "접근 권한 없음 (관리자 권한 필요)", content = @Content(schema = @Schema(example = "{\"error\": {\"code\": \"ACCESS_DENIED\", \"message\": \"관리자 권한이 필요한 요청입니다. 접근 권한이 없습니다.\"}}")))
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
