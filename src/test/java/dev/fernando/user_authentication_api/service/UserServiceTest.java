package dev.fernando.user_authentication_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.fernando.user_authentication_api.dto.CreateUserDto;
import dev.fernando.user_authentication_api.dto.UpdateUserDto;
import dev.fernando.user_authentication_api.dto.ViewUserDto;
import dev.fernando.user_authentication_api.enums.UserRole;
import dev.fernando.user_authentication_api.exception.ChangePasswordIncorrectException;
import dev.fernando.user_authentication_api.exception.UserAlreadyExistException;
import dev.fernando.user_authentication_api.exception.UserNotFoundException;
import dev.fernando.user_authentication_api.model.User;
import dev.fernando.user_authentication_api.publisher.UserPublisher;
import dev.fernando.user_authentication_api.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserPublisher userPublisher;

    @InjectMocks
    private UserService userService;

    private User user;
    private CreateUserDto createUserDto;
    private UpdateUserDto updateUserDto;

    @BeforeEach
    public void setUp() {
        user = new User(1L, "user", "email@test.com", "password", "999999", "333333", 555555L, UserRole.USER);
        createUserDto = new CreateUserDto("user", "email@test.com", "password", "999999", "333333", Date.from(Instant.now()));
        updateUserDto = new UpdateUserDto("user", "password", "newPassword", "999999");
    }

    @Test
    void findUserById() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        ViewUserDto expected = user.toViewUserDto();
        ViewUserDto result = userService.findUserById(user.getId());

        assertNotNull(result);
        assertEquals(expected.username(), result.username());
        assertEquals(expected.email(), result.email());
        verify(userRepository).findById(user.getId());
    }

    @Test
    void findUserByEmail() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        ViewUserDto expected = user.toViewUserDto();
        ViewUserDto result = userService.findUserByEmail(user.getEmail());

        assertNotNull(result);
        assertEquals(expected.username(), result.username());
        assertEquals(expected.email(), result.email());
        verify(userRepository).findByEmail(user.getEmail());
    }

    @Test
    void createUser() {
        when(userRepository.findByEmail(createUserDto.email())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setId(1L);
            return u;
        });
        when(passwordEncoder.encode(createUserDto.password())).thenReturn("hashedPassword");

        User expectedUser = createUserDto.toUser();
        expectedUser.setId(1L);
        expectedUser.setPassword("hashedPassword");
        expectedUser.setUserRole(UserRole.USER);

        ViewUserDto expected = user.toViewUserDto();

        ViewUserDto result = userService.createUserWithDefaultRole(createUserDto);

        assertNotNull(result);
        assertEquals(expected.username(), result.username());
        assertEquals(expected.email(), result.email());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUser() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(updateUserDto.oldPassword(), user.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(updateUserDto.newPassword())).thenReturn("hashedNewPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ViewUserDto result = userService.updateUser(user.getId(), updateUserDto);

        assertNotNull(result);
        assertEquals(updateUserDto.username(), result.username());
        assertEquals(updateUserDto.phone(), result.phone());
        verify(userRepository).save(any(User.class));
    }


    @Test
    void deleteUserById() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        ViewUserDto expected = user.toViewUserDto();
        ViewUserDto result = userService.deleteUserById(user.getId());

        assertNotNull(result);
        assertEquals(expected.username(), result.username());
        assertEquals(expected.email(), result.email());
        verify(userRepository).deleteById(user.getId());
    }

    @Test
    void deleteUserByEmail() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        ViewUserDto expected = user.toViewUserDto();
        ViewUserDto result = userService.deleteUserByEmail(user.getEmail());

        assertNotNull(result);
        assertEquals(expected.username(), result.username());
        assertEquals(expected.email(), result.email());
        verify(userRepository).deleteById(user.getId());
    }
    @Test
    void findUserById_shouldThrowUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findUserById(99L));
    }

    @Test
    void findUserByEmail_shouldThrowUserNotFound() {
        when(userRepository.findByEmail("notfound@test.com")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findUserByEmail("notfound@test.com"));
    }

    @Test
    void createUserWithDefaultRole_shouldThrowUserAlreadyExist() {
        when(userRepository.findByEmail(createUserDto.email())).thenReturn(Optional.of(user));
        assertThrows(UserAlreadyExistException.class, () -> userService.createUserWithDefaultRole(createUserDto));
    }

    @Test
    void updateUser_shouldThrowChangePasswordIncorrect() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(updateUserDto.oldPassword(), user.getPassword())).thenReturn(false);
        assertThrows(ChangePasswordIncorrectException.class, () -> userService.updateUser(user.getId(), updateUserDto));
    }
}