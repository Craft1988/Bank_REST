package com.example.bankcards.mapper;

import com.example.bankcards.dto.auth.request.AuthRequestDto;
import com.example.bankcards.dto.user.response.UserDto;
import com.example.bankcards.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", expression = "java(com.example.bankcards.entity.enums.Role.ROLE_USER)")
    User toUser(AuthRequestDto requestDto);

    UserDto toDto(User user);

}
