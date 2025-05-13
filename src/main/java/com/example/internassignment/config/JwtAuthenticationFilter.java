package com.example.internassignment.config;

import com.example.internassignment.dto.AuthUser;
import com.example.internassignment.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpRequest,
            @NonNull HttpServletResponse httpResponse,
            @NonNull FilterChain chain
    ) throws ServletException, IOException {

        String uri = httpRequest.getRequestURI();
        if (uri.startsWith("/swagger-ui")
                || uri.equals("/swagger-ui.html")
                || uri.startsWith("/v3/api-docs")) {
            chain.doFilter(httpRequest, httpResponse);
            return;
        }

        String authorizationHeader = httpRequest.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = jwtUtil.substringToken(authorizationHeader);

            if (jwt.isBlank()) {
                log.warn("빈 토큰이 전달되었습니다.");
                chain.doFilter(httpRequest, httpResponse);
                return;
            }

            try {
                Claims claims = jwtUtil.extractClaims(jwt);

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    setAuthentication(claims);
                }

                log.info(" SecurityContext 인증 객체: {}", SecurityContextHolder.getContext().getAuthentication());

            } catch (SecurityException | MalformedJwtException e) {
                log.error("Invalid JWT signature", e);
                writeJsonError(httpResponse, "INVALID_TOKEN", "유효하지 않은 인증 토큰입니다.");
                return;
            } catch (ExpiredJwtException e) {
                log.error("Expired JWT token", e);
                writeJsonError(httpResponse, "INVALID_TOKEN", "만료된 인증 토큰입니다.");
                return;
            } catch (UnsupportedJwtException e) {
                log.error("Unsupported JWT token", e);
                writeJsonError(httpResponse, "INVALID_TOKEN", "지원되지 않는 인증 토큰입니다.");
                return;
            } catch (Exception e) {
                log.error("JWT 관련 기타 오류", e);
                writeJsonError(httpResponse, "INTERNAL_ERROR", "서버 내부 오류가 발생했습니다.");
                return;
            }
        }

        chain.doFilter(httpRequest, httpResponse);
    }

    private void setAuthentication(Claims claims) {
        Long userId = Long.valueOf(claims.getSubject());
        String username = claims.get("username", String.class);
        UserRole userRole = UserRole.of(claims.get("userRole", String.class));

        log.info("JWT Claims: userId={}, username={}, role={}", userId, username, userRole);

        AuthUser authUser = new AuthUser(userId, username, userRole);
        JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(authUser);

        log.info("인증 객체 생성: {}", authenticationToken.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private void writeJsonError(HttpServletResponse response, String code, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String json = String.format("""
        {
          "error": {
            "code": "%s",
            "message": "%s"
          }
        }
        """, code, message);
        response.getWriter().write(json);
    }
}
