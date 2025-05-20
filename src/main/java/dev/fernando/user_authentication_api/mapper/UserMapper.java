package dev.fernando.user_authentication_api.mapper;

import dev.fernando.user_authentication_api.dto.CreateUserDto;
import dev.fernando.user_authentication_api.dto.ViewUserDto;
import dev.fernando.user_authentication_api.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Date;

@Mapper(componentModel = "spring")
public interface UserMapper {

    ViewUserDto userToViewUserDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(source = "birthday_date", target = "birthday_date", qualifiedByName = "dateToEpoch")
    User createUserDtoToUser(CreateUserDto createUserDto);

    @Named("dateToEpoch")
    static Long mapDateToEpoch(Date date) {
        return date.getTime();
    }
}
