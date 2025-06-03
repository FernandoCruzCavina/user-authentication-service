package dev.fernando.user_authentication_api.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import dev.fernando.user_authentication_api.dto.CreateUserDto;
import dev.fernando.user_authentication_api.model.User;

class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    Date date = Date.from(Instant.now());

    @Test
    void createUserDtoToUser() {
        CreateUserDto createUserDto = new CreateUserDto(
                "name",
                "e@m.com",
                "123",
                "999",
                "222",
                date
        );

        User user = userMapper.createUserDtoToUser(createUserDto);

        assertNotNull(user);
        assertEquals(user.getPassword(), createUserDto.password());
        assertEquals(user.getUsername(), createUserDto.username());
        assertEquals(user.getPhone(), createUserDto.phone());
        assertEquals(user.getEmail(), createUserDto.email());
        assertEquals(user.getBirthday_date(), date.getTime());
    }
}