package dev.fernando.user_authentication_api.mapper;

import dev.fernando.user_authentication_api.dto.CreateUserDto;
import dev.fernando.user_authentication_api.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserMapperTest {

    private UserMapper userMapper;

    @Test
    void createUserDtoToUser() {
        userMapper = new UserMapperImpl();

        CreateUserDto createUserDto = new CreateUserDto(
                "name",
                "e@m.com",
                "123",
                "999"
        );

        User user = userMapper.createUserDtoToUser(createUserDto);

        assertNotNull(user);
        assertEquals(user.getPassword(), createUserDto.password());
        assertEquals(user.getUsername(), createUserDto.username());
        assertEquals(user.getPhone(), createUserDto.phone());
        assertEquals(user.getEmail(), createUserDto.email());
    }
}