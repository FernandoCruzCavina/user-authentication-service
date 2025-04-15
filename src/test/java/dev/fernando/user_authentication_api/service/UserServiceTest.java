package dev.fernando.user_authentication_api.service;

import dev.fernando.user_authentication_api.constants.UserRole;
import dev.fernando.user_authentication_api.dto.CreateUserDto;
import dev.fernando.user_authentication_api.dto.UpdateUserDto;
import dev.fernando.user_authentication_api.dto.ViewUserDto;
import dev.fernando.user_authentication_api.entity.User;
import dev.fernando.user_authentication_api.mapper.UserMapper;
import dev.fernando.user_authentication_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private static User user;
    private static ViewUserDto expectedViewUserDto;
    private static CreateUserDto createUserDto;
    private static UpdateUserDto updateUserDto;

    @BeforeEach
    public void setUp() {
        user = new User(1, "user", "email@test.com", "password", "999999", UserRole.USER);
        expectedViewUserDto = new ViewUserDto(1, "user", "email@test.com", "999999");
        createUserDto = new CreateUserDto("user", "email@test.com", "password", "999999");
        updateUserDto = new UpdateUserDto("user", "email@test.com", "password", "999999");
    }

    @Test
    void loginUser() {
    }

    @Test
    void findUserById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userMapper.userToViewUserDto(user)).thenReturn(expectedViewUserDto);

        ViewUserDto result = userService.findUserById(1);

        assertNotNull(result);
        assertEquals(expectedViewUserDto, result);
        verify(userRepository).findById(1);
    }

    @Test
    void findUserByEmail() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userMapper.userToViewUserDto(user)).thenReturn(expectedViewUserDto);

        ViewUserDto result = userService.findUserByEmail(user.getEmail());

        assertNotNull(result);
        assertEquals(expectedViewUserDto, result);
        verify(userRepository).findByEmail(user.getEmail());
    }

    @Test
    void createUser() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userMapper.createUserDtoToUser(createUserDto)).thenReturn(user);
        when(userMapper.userToViewUserDto(user)).thenReturn(expectedViewUserDto);

        ViewUserDto result = userService.createUser(createUserDto);

        assertNotNull(result);
        assertEquals(expectedViewUserDto, result);
        verify(userRepository).save(user);
    }

    @Test
    void updateUser() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userMapper.userToViewUserDto(user)).thenReturn(expectedViewUserDto);

        ViewUserDto result = userService.updateUser(updateUserDto);

        assertNotNull(result);
        assertEquals(expectedViewUserDto, result);
        verify(userRepository).save(user);
    }

    @Test
    void deleteUserById() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userMapper.userToViewUserDto(user)).thenReturn(expectedViewUserDto);

        ViewUserDto result = userService.deleteUserById(user.getId());

        assertNotNull(result);
        assertEquals(expectedViewUserDto, result);
        verify(userRepository).deleteById(user.getId());
    }

    @Test
    void deleteUserByEmail() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userMapper.userToViewUserDto(user)).thenReturn(expectedViewUserDto);

        ViewUserDto result = userService.deleteUserByEmail(user.getEmail());

        assertNotNull(result);
        assertEquals(expectedViewUserDto, result);
        verify(userRepository).deleteById(user.getId());
    }
}