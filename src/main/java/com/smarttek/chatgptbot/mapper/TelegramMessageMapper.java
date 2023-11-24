package com.smarttek.chatgptbot.mapper;

import com.smarttek.chatgptbot.config.MapperConfig;
import com.smarttek.chatgptbot.dto.telegram.TelegramMessageDto;
import com.smarttek.chatgptbot.model.TelegramMessage;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface TelegramMessageMapper {
    TelegramMessageDto toDto(TelegramMessage telegramMessage);
}
