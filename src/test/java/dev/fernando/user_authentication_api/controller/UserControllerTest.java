package dev.fernando.user_authentication_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.fernando.user_authentication_api.enums.UserRole;
import dev.fernando.user_authentication_api.dto.CreateUserDto;
import dev.fernando.user_authentication_api.dto.UpdateUserDto;
import dev.fernando.user_authentication_api.model.User;
import dev.fernando.user_authentication_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    private User createTestUser(String email) {
        User user = new User("user", email, passwordEncoder.encode("password"), "999999", UserRole.ADMIN);
        return userRepository.save(user);
    }

    @Test
    void testCreateUser() throws Exception {
        CreateUserDto createUserDto = new CreateUserDto("user", "email@test.com", "password", "999999", "222222", Date.from(Instant.now()));

        String json = objectMapper.writeValueAsString(createUserDto);


        mockMvc.perform(post("/user")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("user"))
                .andExpect(jsonPath("$.email").value("email@test.com"));
    }

    @Test
    void testGetUserById() throws Exception {
        User user = createTestUser("test@example.com");

        User user1 = userRepository.save(new User("user", "email@test.com", "password", "999999"));

        mockMvc.perform(get("/user/id=" + user1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("email@test.com"))
                .andExpect(jsonPath("$.username").value("user"));
    }

    @Test
    void testGetUserByEmail() throws Exception {
        User user = createTestUser("test@example.com");

        userRepository.save(new User("user", "email@test.com", "password", "999999"));

        mockMvc.perform(get("/user/email=email@test.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("email@test.com"))
                .andExpect(jsonPath("$.username").value("user"));
    }

    @Test
    void testUpdateUser() throws Exception {
        User user = createTestUser("test@example.com");

        var userSaved = userRepository.save(new User("user", "email@test.com", "password", "999999"));

        UpdateUserDto updateUserDto = new UpdateUserDto("updatedUser","newPassword","888888");
        String json = objectMapper.writeValueAsString(updateUserDto);

        mockMvc.perform(put("/user/id=" + userSaved.getId())
                        .contentType("application/json")
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("updatedUser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("888888"));
    }

    @Test
    void testDeleteUserById() throws Exception {
        User user = createTestUser("test@example.com");

        User user1 = userRepository.save(new User("user", "email@test.com", "password", "999999"));

        mockMvc.perform(delete("/user/id=" + user1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("email@test.com"));
    }

    @Test
    void testDeleteUserByEmail() throws Exception {
        User user = createTestUser("test@example.com");

        userRepository.save(new User("user", "email@test.com", "password", "999999"));

        mockMvc.perform(delete("/user/email=email@test.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("email@test.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("user"));
    }
}
