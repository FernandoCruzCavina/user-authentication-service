package dev.fernando.user_authentication_api.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.fernando.user_authentication_api.constants.UserRole;
import dev.fernando.user_authentication_api.dto.JwtUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JwtUtilsTest {

    private JwtUtils jwtUtils;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final JwtUserDto jwtUserDto =
            new JwtUserDto(1, "user", "email@test.com", "999999", UserRole.USER);


    @BeforeEach
    public void setUp() {
        jwtUtils = new JwtUtils("secretkey", "test", 1000 * 60);
    }


    @Test
    void generateToken() throws JsonProcessingException {
        String token = jwtUtils.generateToken(jwtUserDto);
        assertNotNull(token);

        String userJson = jwtUtils.verifyToken(token);
        assertNotNull(userJson);

        JwtUserDto userFromJson = objectMapper.readValue(userJson, JwtUserDto.class);
        assertNotNull(userFromJson);
        assertEquals(jwtUserDto, userFromJson);

    }

}