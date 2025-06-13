package dev.fernando.user_authentication_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.fernando.user_authentication_api.dto.AuthUserDto;
import dev.fernando.user_authentication_api.dto.CreateUserDto;
import dev.fernando.user_authentication_api.dto.UpdateUserDto;
import dev.fernando.user_authentication_api.dto.ViewUserDto;
import dev.fernando.user_authentication_api.enums.CreationType;
import dev.fernando.user_authentication_api.enums.UserRole;
import dev.fernando.user_authentication_api.exception.ChangePasswordIncorrectException;
import dev.fernando.user_authentication_api.exception.UserAlreadyExistException;
import dev.fernando.user_authentication_api.exception.UserNotFoundException;
import dev.fernando.user_authentication_api.model.User;
import dev.fernando.user_authentication_api.publisher.UserPublisher;
import dev.fernando.user_authentication_api.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserPublisher userEventPublisher;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            UserPublisher userEventPublisher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userEventPublisher = userEventPublisher;
    }

    public ViewUserDto findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        return user.toViewUserDto();
    }

    public ViewUserDto findUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        return user.toViewUserDto();
    }

    @Transactional
    public ViewUserDto createUserWithDefaultRole(CreateUserDto createUserDto) {

        userRepository.findByEmail(createUserDto.email()).ifPresent(u -> {throw new UserAlreadyExistException(u);});

        User user = createUserDto.toUser();
        String hashedPassword = passwordEncoder.encode(createUserDto.password());
        user.setPassword(hashedPassword);
        user.setUserRole(UserRole.USER);
        User createdUser = userRepository.save(user);

        userEventPublisher.publishMessageEmail(createdUser);
        userEventPublisher.publishAccountCreation(createdUser.getId());
        userEventPublisher.publishUserCredentials(new AuthUserDto(createdUser.getEmail(), createdUser.getPassword(), UserRole.USER));
        userEventPublisher.publishUserEvent(user.convertToUserEventDto(), CreationType.CREATEACCOUNT);

        return user.toViewUserDto();
    }

    @Transactional
    public ViewUserDto updateUser(Long id, UpdateUserDto updateUserDto) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        boolean isTheSamePassword = passwordEncoder.matches(updateUserDto.oldPassword(), user.getPassword());

        if (!isTheSamePassword) {
            throw new ChangePasswordIncorrectException();
        }

        String hashedPassword = passwordEncoder.encode(updateUserDto.newPassword());

        user.setUsername(updateUserDto.username());
        user.setPassword(hashedPassword);
        user.setPhone(updateUserDto.phone());

        userRepository.save(user);

        return user.toViewUserDto();
    }

    @Transactional
    public ViewUserDto deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        userRepository.deleteById(id);

        return user.toViewUserDto();
    }

    @Transactional
    public ViewUserDto deleteUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        userRepository.deleteById(user.getId());

        return user.toViewUserDto();
    }
}
