package dev.fernando.user_authentication_api.mapper;

import dev.fernando.user_authentication_api.dto.CreateUserDto;
import dev.fernando.user_authentication_api.dto.JwtUserDto;
import dev.fernando.user_authentication_api.dto.ViewUserDto;
import dev.fernando.user_authentication_api.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    ViewUserDto userToViewUserDto(User user);

    @Mapping(source = "email", target = "email")
    User createUserDtoToUser(CreateUserDto createUserDto);

    JwtUserDto userToJwtUserDto(User user);
}
