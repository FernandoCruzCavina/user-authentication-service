package dev.fernando.user_authentication_api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.fernando.user_authentication_api.config.RabbitMQTestConfig;
import dev.fernando.user_authentication_api.dto.CreateUserDto;
import dev.fernando.user_authentication_api.dto.UpdateUserDto;
import dev.fernando.user_authentication_api.enums.UserRole;
import dev.fernando.user_authentication_api.model.User;
import dev.fernando.user_authentication_api.repository.UserRepository;

@SpringBootTest
@Import(RabbitMQTestConfig.class)
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

    @Test
    void testCreateUser() throws Exception {
        CreateUserDto createUserDto = new CreateUserDto(
                "user", "email@test.com", "password", "999999", "222222", Date.from(Instant.now()));
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
        User user = userRepository.save(new User("user", "email@test.com", "password", "999999", "222222", Date.from(Instant.now()).getTime(), UserRole.USER));

        mockMvc.perform(get("/user/id/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user"))
                .andExpect(jsonPath("$.email").value("email@test.com"))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void testGetUserByEmail() throws Exception {
        userRepository.save(new User("user", "email@test.com", "password", "999999", "222222", Date.from(Instant.now()).getTime(), UserRole.USER));

        mockMvc.perform(get("/user/email/email@test.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("email@test.com"))
                .andExpect(jsonPath("$.username").value("user"))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void testUpdateUser() throws Exception {
        User user = userRepository.save(new User("user", "email@test.com", passwordEncoder.encode("password"), "999999", "222222", Date.from(Instant.now()).getTime(), UserRole.USER));

        UpdateUserDto updateUserDto = new UpdateUserDto("updatedUser", "password", "newPassword", "888888");
        String json = objectMapper.writeValueAsString(updateUserDto);

        mockMvc.perform(put("/user/id/" + user.getId())
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("updatedUser"))
                .andExpect(jsonPath("$.phone").value("888888"))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void testDeleteUserById() throws Exception {
        User user = userRepository.save(new User("user", "email@test.com", "password", "999999", "222222", Date.from(Instant.now()).getTime(), UserRole.USER));

        mockMvc.perform(delete("/user/id/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("email@test.com"))
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void testDeleteUserByEmail() throws Exception {
        userRepository.save(new User("user", "email@test.com", "password", "999999", "222222", Date.from(Instant.now()).getTime(), UserRole.USER));

        mockMvc.perform(delete("/user/email/email@test.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user"))
                .andExpect(jsonPath("$.email").value("email@test.com"))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void testCreateUser_whenEmailAlreadyExists_shouldReturn422() throws Exception {
        CreateUserDto createUserDto = new CreateUserDto(
                "user", "email@test.com", "password", "999999", "222222", Date.from(Instant.now()));

        userRepository.save(createUserDto.toUser());

        String json = objectMapper.writeValueAsString(createUserDto);

        mockMvc.perform(post("/user")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").value("Usuário com email email@test.com já existe"));
    }

    @Test
    void testGetUserById_whenNotFound_shouldReturn404() throws Exception {
        mockMvc.perform(get("/user/id/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Usuário não encontrado"));
    }

    @Test
    void testGetUserByEmail_whenNotFound_shouldReturn404() throws Exception {
        mockMvc.perform(get("/user/email/nonexistent@email.com"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Usuário não encontrado"));
    }

    @Test
    void testUpdateUser_whenOldPasswordIncorrect_shouldReturn422() throws Exception {
        User user = userRepository.save(new User("user", "email@test.com", passwordEncoder.encode("correctPassword"),
                "999999", "222222", Date.from(Instant.now()).getTime(), UserRole.USER));

        UpdateUserDto updateUserDto = new UpdateUserDto("newUsername", "wrongPassword", "newPass123", "888888");
        String json = objectMapper.writeValueAsString(updateUserDto);

        mockMvc.perform(put("/user/id/" + user.getId())
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").value("A senha atual está incorreta"));
    }

    @Test
    void testDeleteUserById_whenUserDoesNotExist_shouldReturn404() throws Exception {
        mockMvc.perform(delete("/user/id/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Usuário não encontrado"));
    }

}
