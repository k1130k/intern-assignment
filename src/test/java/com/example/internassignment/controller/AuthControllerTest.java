package com.example.internassignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws Exception {

        String adminSignup = """
        {
            "username": "admin_user",
            "password": "1234",
            "nickname": "관리자"
        }
        """;
        mockMvc.perform(post("/admin/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(adminSignup));
    }

    @Test
    @DisplayName("회원가입 성공 및 중복 가입 실패")
    void signupTest() throws Exception {
        String request = """
        {
            "username": "user_signup",
            "password": "pass1234",
            "nickname": "테스트유저"
        }
        """;


        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user_signup"));


        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value("USER_ALREADY_EXISTS"));
    }

    @Test
    @DisplayName("로그인 성공 및 실패")
    void loginTest() throws Exception {
        String signup = """
        {
            "username": "login_test_user",
            "password": "1234",
            "nickname": "유저"
        }
        """;

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signup))
                .andExpect(status().isOk());

        String loginSuccess = """
        {
            "username": "login_test_user",
            "password": "1234"
        }
        """;

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginSuccess))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());

        String loginFail = """
        {
            "username": "login_test_user",
            "password": "wrongpass"
        }
        """;

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginFail))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value("INVALID_CREDENTIALS"));
    }

    @Test
    @DisplayName("관리자 권한 부여 성공 및 권한 부족 실패")
    void promoteToAdminTest() throws Exception {
        // 일반 유저 가입
        String userSignup = """
        {
            "username": "normal_user",
            "password": "1234",
            "nickname": "유저2"
        }
        """;
        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userSignup));


        String loginBody = """
        {
            "username": "admin_user",
            "password": "1234"
        }
        """;

        MvcResult result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginBody))
                .andExpect(status().isOk())
                .andReturn();

        String rawToken = JsonPath.read(result.getResponse().getContentAsString(), "$.token");
        String token = rawToken.replace("Bearer ", "");


        mockMvc.perform(patch("/admin/users/2/roles")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roles[0].role").value("Admin"));


        String userLogin = """
        {
            "username": "normal_user",
            "password": "1234"
        }
        """;
        MvcResult result2 = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userLogin))
                .andExpect(status().isOk())
                .andReturn();

        String rawUserToken = JsonPath.read(result2.getResponse().getContentAsString(), "$.token");
        String userToken = rawUserToken.replace("Bearer ", "");

        mockMvc.perform(patch("/admin/users/1/roles")
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error.code").value("ACCESS_DENIED"));
    }
}