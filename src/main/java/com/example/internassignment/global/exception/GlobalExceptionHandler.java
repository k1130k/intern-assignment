package com.example.internassignment.global.exception;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<Map<String, Object>> handleClientException(ClientException e) {
        return ResponseEntity.badRequest().body(Map.of(
                "error", Map.of(
                        "code", e.getCode(),
                        "message", e.getMessage()
                )
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception e) {
        log.error("서버 내부 오류 발생", e);
        return ResponseEntity.internalServerError().body(Map.of(
                "error", Map.of(
                        "code", "INTERNAL_ERROR",
                        "message", "서버 내부 오류가 발생했습니다."
                )
        ));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException e) {
        return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(Map.of(
                "error", Map.of(
                        "code", "ACCESS_DENIED",
                        "message", "관리자 권한이 필요한 요청입니다. 접근 권한이 없습니다."
                )
        ));
    }
}
