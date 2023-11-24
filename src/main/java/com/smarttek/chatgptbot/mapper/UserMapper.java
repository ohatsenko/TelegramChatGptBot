package com.smarttek.chatgptbot.mapper;

import com.smarttek.chatgptbot.config.MapperConfig;
import com.smarttek.chatgptbot.dto.user.UserDto;
import com.smarttek.chatgptbot.dto.user.UserRegistrationRequestDto;
import com.smarttek.chatgptbot.dto.user.UserRegistrationResponseDto;
import com.smarttek.chatgptbot.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserRegistrationResponseDto toUserResponse(User user);

    UserDto toDto(User user);

    User toModel(UserRegistrationRequestDto userDto);
}
