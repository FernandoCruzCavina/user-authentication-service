package dev.fernando.user_authentication_api.service;

import dev.fernando.user_authentication_api.dto.*;
import dev.fernando.user_authentication_api.entity.User;
import dev.fernando.user_authentication_api.exception.InvalidUserCredentials;
import dev.fernando.user_authentication_api.exception.UserAlreadyExist;
import dev.fernando.user_authentication_api.exception.UserNotFound;
import dev.fernando.user_authentication_api.mapper.UserMapper;
import dev.fernando.user_authentication_api.repository.UserRepository;
import dev.fernando.user_authentication_api.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Cacheable(value = "usersByEmail", key = "loginUserDto.email()")
    public String loginUser(LoginUserDto loginUserDto) {
        User user = userRepository.findByEmail(loginUserDto.email())
                .orElseThrow(UserNotFound::new);

        login(user, loginUserDto);

        JwtUserDto jwtUserDto = userMapper.userToJwtUserDto(user);

        return jwtUtils.generateToken(jwtUserDto);
    }

    private void login(User user, LoginUserDto loginUserDto) {
        String userEmail = user.getEmail();
        String userPassword = user.getPassword();

        if (!passwordEncoder.matches(loginUserDto.password(), userPassword)) {
            throw new InvalidUserCredentials();
        }

        if (!userEmail.equals(loginUserDto.email())) {
            throw new InvalidUserCredentials();
        }
    }

    @Cacheable(value = "usersById", key = "#id")
    public ViewUserDto findUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFound::new);

        return userMapper.userToViewUserDto(user);
    }

    @Cacheable(value = "usersByEmail", key = "#email")
    public ViewUserDto findUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFound::new);

        return userMapper.userToViewUserDto(user);
    }

    @CachePut(value = "usersByEmail", key = "#createUserDto.email()")
    public ViewUserDto createUser(CreateUserDto createUserDto) {

        userRepository.findByEmail(createUserDto.email()).ifPresent(UserAlreadyExist::new);

        User user = userMapper.createUserDtoToUser(createUserDto);
        String hashedPassword = passwordEncoder.encode(createUserDto.password());
        user.setPassword(hashedPassword);

        userRepository.save(user);

        return userMapper.userToViewUserDto(user);
    }

    @CachePut(value = "usersByEmail", key = "#updateUserDto.email()")
    public ViewUserDto updateUser(UpdateUserDto updateUserDto) {
        User user = userRepository.findByEmail(updateUserDto.email())
                .orElseThrow(UserNotFound::new);

        user.setUsername(updateUserDto.username());
        user.setPassword(updateUserDto.password());
        user.setPhone(updateUserDto.phone());

        userRepository.save(user);

        return userMapper.userToViewUserDto(user);
    }

    @CacheEvict(value = "usersById", key = "#id")
    public ViewUserDto deleteUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFound::new);

        userRepository.deleteById(id);

        return userMapper.userToViewUserDto(user);
    }

    @CacheEvict(value = "usersByEmail", key = "#email")
    public ViewUserDto deleteUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFound::new);

        userRepository.deleteById(user.getId());

        return userMapper.userToViewUserDto(user);
    }
}
