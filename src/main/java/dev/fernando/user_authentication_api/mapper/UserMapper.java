package dev.fernando.user_authentication_api.mapper;

import dev.fernando.user_authentication_api.dto.CreateUserDto;
import dev.fernando.user_authentication_api.dto.ViewUserDto;
import dev.fernando.user_authentication_api.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    ViewUserDto userToViewUserDto(User user);
    User createUserDtoToUser(CreateUserDto createUserDto);
}
