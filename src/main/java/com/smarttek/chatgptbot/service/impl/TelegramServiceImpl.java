package com.smarttek.chatgptbot.service.impl;

import com.smarttek.chatgptbot.dto.telegram.TelegramMessageDto;
import com.smarttek.chatgptbot.dto.telegram.TelegramUserDto;
import com.smarttek.chatgptbot.mapper.TelegramMessageMapper;
import com.smarttek.chatgptbot.mapper.TelegramUserMapper;
import com.smarttek.chatgptbot.repository.TelegramMessageRepository;
import com.smarttek.chatgptbot.repository.TelegramUserRepository;
import com.smarttek.chatgptbot.service.TelegramService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelegramServiceImpl implements TelegramService {
    private final TelegramUserRepository telegramUserRepository;
    private final TelegramMessageRepository telegramMessageRepository;
    private final TelegramUserMapper telegramUserMapper;
    private final TelegramMessageMapper telegramMessageMapper;

    @Override
    public List<TelegramUserDto> getAllUsers(Pageable pageable) {
        return telegramUserRepository.findAll(pageable)
                .stream()
                .map(telegramUserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TelegramMessageDto> getUserMessages(Long id, Pageable pageable) {
        return telegramMessageRepository.findAllByTelegramUserId(id, pageable)
                .stream()
                .map(telegramMessageMapper::toDto)
                .collect(Collectors.toList());
    }
}
