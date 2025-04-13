package dev.fernando.user_authentication_api.service;

import dev.fernando.user_authentication_api.dto.CreateUserDto;
import dev.fernando.user_authentication_api.dto.LoginUserDto;
import dev.fernando.user_authentication_api.dto.UpdateUserDto;
import dev.fernando.user_authentication_api.dto.ViewUserDto;
import dev.fernando.user_authentication_api.entity.User;
import dev.fernando.user_authentication_api.exception.InvalidUserCredentials;
import dev.fernando.user_authentication_api.exception.UserAlreadyExist;
import dev.fernando.user_authentication_api.exception.UserNotFound;
import dev.fernando.user_authentication_api.mapper.UserMapper;
import dev.fernando.user_authentication_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public String loginUser(LoginUserDto loginUserDto){

        User user = userRepository.findByEmail(loginUserDto.email())
                .orElseThrow(UserNotFound::new);

        login(user, loginUserDto);

        return "token";
    }

    private static void login(User user, LoginUserDto loginUserDto) {
        String userEmail = user.getEmail();
        String userPassword = user.getPassword();

        if(userEmail.equals(loginUserDto.email()) && userPassword.equals(loginUserDto.password())){
            throw new InvalidUserCredentials();
        }
    }

    public ViewUserDto findUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFound::new);

        return userMapper.userToViewUserDto(user);
    }

    public ViewUserDto findUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFound::new);

        return userMapper.userToViewUserDto(user);
    }

    public ViewUserDto createUser(CreateUserDto createUserDto) {

        userRepository.findByEmail(createUserDto.email()).ifPresent(UserAlreadyExist::new);

        User user = userMapper.createUserDtoToUser(createUserDto);

        userRepository.save(user);

        return userMapper.userToViewUserDto(user);
    }

    public ViewUserDto updateUser(UpdateUserDto updateUserDto) {
        User user = userRepository.findByEmail(updateUserDto.email())
                .orElseThrow(UserNotFound::new);

        user.setUsername(updateUserDto.username());
        user.setPassword(updateUserDto.password());
        user.setPhone(updateUserDto.phone());

        userRepository.save(user);

        return userMapper.userToViewUserDto(user);
    }

    public ViewUserDto deleteUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFound::new);

        userRepository.deleteById(id);

        return userMapper.userToViewUserDto(user);
    }

    public ViewUserDto deleteUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFound::new);

        userRepository.deleteById(user.getId());

        return userMapper.userToViewUserDto(user);
    }
}
