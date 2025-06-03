package dev.fernando.user_authentication_api.mapper;

import java.util.Date;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import dev.fernando.user_authentication_api.dto.CreateUserDto;
import dev.fernando.user_authentication_api.dto.ViewUserDto;
import dev.fernando.user_authentication_api.model.User;


@Mapper(componentModel = "spring")
public interface UserMapper {
    
    ViewUserDto userToViewUserDto(User user);
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "birthday_date", target = "birthday_date", qualifiedByName = "dateToEpoch")
    User createUserDtoToUser(CreateUserDto createUserDto);


    @Named("dateToEpoch")
    default Long dateToEpoch(Date date) {
        return date == null ? null : date.getTime();
    }
}
