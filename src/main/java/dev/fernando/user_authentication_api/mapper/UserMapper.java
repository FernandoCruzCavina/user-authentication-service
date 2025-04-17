package dev.fernando.user_authentication_api.mapper;

import dev.fernando.user_authentication_api.dto.CreateUserDto;
import dev.fernando.user_authentication_api.dto.JwtUserDto;
import dev.fernando.user_authentication_api.dto.ViewUserDto;
import dev.fernando.user_authentication_api.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Date;

@Mapper(componentModel = "spring")
public interface UserMapper {

    ViewUserDto userToViewUserDto(User user);

    @Mapping(source = "email", target = "email")
    @Mapping(source = "birthday_date", target = "birthday_date", qualifiedByName = "dateToEpoch")
    User createUserDtoToUser(CreateUserDto createUserDto);

    JwtUserDto userToJwtUserDto(User user);

    @Named("dateToEpoch")
    static Long mapDateToEpoch(Date date) {
        return date.getTime();
    }
}
