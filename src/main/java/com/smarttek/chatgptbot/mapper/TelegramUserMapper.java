package com.smarttek.chatgptbot.mapper;

import com.smarttek.chatgptbot.config.MapperConfig;
import com.smarttek.chatgptbot.dto.telegram.TelegramUserDto;
import com.smarttek.chatgptbot.model.TelegramUser;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface TelegramUserMapper {
    TelegramUserDto toDto(TelegramUser telegramUser);
}
