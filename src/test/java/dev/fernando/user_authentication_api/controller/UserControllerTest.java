package dev.fernando.user_authentication_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.fernando.user_authentication_api.constants.UserRole;
import dev.fernando.user_authentication_api.dto.CreateUserDto;
import dev.fernando.user_authentication_api.dto.JwtUserDto;
import dev.fernando.user_authentication_api.dto.LoginUserDto;
import dev.fernando.user_authentication_api.dto.UpdateUserDto;
import dev.fernando.user_authentication_api.entity.User;
import dev.fernando.user_authentication_api.repository.UserRepository;
import dev.fernando.user_authentication_api.security.JwtUtils;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    private static String token;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    private User createTestUser(String email) {
        User user = new User("user", email, passwordEncoder.encode("password"), "999999", UserRole.ADMIN);
        return userRepository.save(user);
    }

    private String generateToken(User user) {
        JwtUserDto jwtUserDto = new JwtUserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getUser_role()
        );
        return jwtUtils.generateToken(jwtUserDto);
    }

    @Test
    void testCreateUser() throws Exception {
        User user = createTestUser("test@example.com");
        token = generateToken(user);

        CreateUserDto createUserDto = new CreateUserDto("user", "email@test.com", "password", "999999", UserRole.USER);

        String json = objectMapper.writeValueAsString(createUserDto);


        mockMvc.perform(post("/user/create")
                        .header("Authorization", token)
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("user"))
                .andExpect(jsonPath("$.email").value("email@test.com"));
    }

    @Test
    void testGetUserById() throws Exception {
        User user = createTestUser("test@example.com");
        token = generateToken(user);

        User user1 = userRepository.save(new User("user", "email@test.com", "password", "999999"));

        mockMvc.perform(get("/user/get/id=" + user1.getId())
                        .header("Authorization",token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("email@test.com"))
                .andExpect(jsonPath("$.username").value("user"));
    }

    @Test
    void testGetUserByEmail() throws Exception {
        User user = createTestUser("test@example.com");
        token = generateToken(user);

        userRepository.save(new User("user", "email@test.com", "password", "999999"));

        mockMvc.perform(get("/user/get/email=email@test.com")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("email@test.com"))
                .andExpect(jsonPath("$.username").value("user"));
    }

    @Test
    void testLogin() throws Exception {
        String password = passwordEncoder.encode("password");
        userRepository.save(new User("user", "email4@test.com", password, "999999", UserRole.USER));

        LoginUserDto loginUserDto = new LoginUserDto("email4@test.com", "password");

        String json = objectMapper.writeValueAsString(loginUserDto);

        mockMvc.perform(post("/user/login")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string(not(isEmptyOrNullString())));
    }

    @Test
    void testUpdateUser() throws Exception {
        User user = createTestUser("test@example.com");
        token = generateToken(user);

        userRepository.save(new User("user", "email@test.com", "password", "999999"));

        UpdateUserDto updateUserDto = new UpdateUserDto("updatedUser","email@test.com","newPassword","888888");
        String json = objectMapper.writeValueAsString(updateUserDto);

        mockMvc.perform(put("/user/update")
                        .header("Authorization", token)
                        .contentType("application/json")
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("updatedUser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("888888"));
    }

    @Test
    void testDeleteUserById() throws Exception {
        User user = createTestUser("test@example.com");
        token = generateToken(user);

        User user1 = userRepository.save(new User("user", "email@test.com", "password", "999999"));

        mockMvc.perform(delete("/user/delete/id=" + user1.getId())
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("email@test.com"));
    }

    @Test
    void testDeleteUserByEmail() throws Exception {
        User user = createTestUser("test@example.com");
        token = generateToken(user);

        userRepository.save(new User("user", "email@test.com", "password", "999999"));

        mockMvc.perform(delete("/user/delete/emai=email@test.com")
                        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("email@test.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("user"));
    }
}
