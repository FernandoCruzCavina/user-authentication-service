package dev.fernando.user_authentication_api.service;

import dev.fernando.user_authentication_api.dto.CreateUserDto;
import dev.fernando.user_authentication_api.dto.UpdateUserDto;
import dev.fernando.user_authentication_api.dto.ViewUserDto;

public interface UserService {

    public ViewUserDto findUserById(Long id);

    public ViewUserDto findUserByEmail(String email);

    public ViewUserDto createUserWithDefaultRole(CreateUserDto createUserDto);

    public ViewUserDto updateUser(Long id, UpdateUserDto updateUserDto);

    public ViewUserDto deleteUserById(Long id);

    public ViewUserDto deleteUserByEmail(String email);
}
