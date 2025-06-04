package dev.fernando.user_authentication_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.fernando.user_authentication_api.dto.AuthUserDto;
import dev.fernando.user_authentication_api.dto.CreateUserDto;
import dev.fernando.user_authentication_api.dto.UpdateUserDto;
import dev.fernando.user_authentication_api.dto.UserEventDto;
import dev.fernando.user_authentication_api.dto.ViewUserDto;
import dev.fernando.user_authentication_api.enums.CreationType;
import dev.fernando.user_authentication_api.enums.UserRole;
import dev.fernando.user_authentication_api.exception.ChangePasswordIncorrect;
import dev.fernando.user_authentication_api.exception.UserAlreadyExist;
import dev.fernando.user_authentication_api.exception.UserNotFound;
import dev.fernando.user_authentication_api.mapper.UserMapper;
import dev.fernando.user_authentication_api.model.User;
import dev.fernando.user_authentication_api.producer.UserProducer;
import dev.fernando.user_authentication_api.producer.UserPublisher;
import dev.fernando.user_authentication_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.netflix.appinfo.InstanceInfo.ActionType;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserProducer userProducer;

    @Autowired
    UserPublisher userEventPublisher;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder,
            UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userProducer = userProducer;
    }

    // @Cacheable(value = "usersById", key = "#id")
    public ViewUserDto findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFound::new);

        return userMapper.userToViewUserDto(user);
    }

    // @Cacheable(value = "usersByEmail", key = "#email")
    public ViewUserDto findUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFound::new);

        return userMapper.userToViewUserDto(user);
    }

    @Transactional
    // @CachePut(value = "usersByEmail", key = "#createUserDto.email()")
    public ViewUserDto createUserWithDefaultRole(CreateUserDto createUserDto) {

        userRepository.findByEmail(createUserDto.email()).ifPresent(UserAlreadyExist::new);

        User user = userMapper.createUserDtoToUser(createUserDto);
        String hashedPassword = passwordEncoder.encode(createUserDto.password());
        user.setPassword(hashedPassword);
        user.setUser_role(UserRole.USER);
        User createdUser = userRepository.save(user);

        userProducer.publishMessageEmail(createdUser);
        userProducer.publishAccountCreation(createdUser.getId());
        userProducer.publishUserCredentials(
                new AuthUserDto(createdUser.getEmail(), createdUser.getPassword(), createdUser.getUser_role()));
        userEventPublisher.publishUserEvent(user.convertToUserEventDto(), CreationType.CREATEACCOUNT);

        return userMapper.userToViewUserDto(user);
    }

    @Transactional
    // @CachePut(value = "usersById", key = "#id")
    public ViewUserDto updateUser(Long id, UpdateUserDto updateUserDto) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFound::new);

        boolean isTheSamePassword = passwordEncoder.matches(updateUserDto.oldPassword(), user.getPassword());

        if (!isTheSamePassword) {
            throw new ChangePasswordIncorrect();
        }

        String hashedPassword = passwordEncoder.encode(updateUserDto.newPassword());

        user.setUsername(updateUserDto.username());
        user.setPassword(hashedPassword);
        user.setPhone(updateUserDto.phone());

        userRepository.save(user);

        return userMapper.userToViewUserDto(user);
    }

    @Transactional
    // @CacheEvict(value = "usersById", key = "#id")
    public ViewUserDto deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFound::new);

        userRepository.deleteById(id);

        return userMapper.userToViewUserDto(user);
    }

    @Transactional
    // @CacheEvict(value = "usersByEmail", key = "#email")
    public ViewUserDto deleteUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFound::new);

        userRepository.deleteById(user.getId());

        return userMapper.userToViewUserDto(user);
    }
}
