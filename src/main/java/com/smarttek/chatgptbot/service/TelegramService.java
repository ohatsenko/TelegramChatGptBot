package com.smarttek.chatgptbot.service;

import com.smarttek.chatgptbot.dto.telegram.TelegramMessageDto;
import com.smarttek.chatgptbot.dto.telegram.TelegramUserDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface TelegramService {
    List<TelegramUserDto> getAllUsers(Pageable pageable);

    List<TelegramMessageDto> getUserMessages(Long id, Pageable pageable);
}
