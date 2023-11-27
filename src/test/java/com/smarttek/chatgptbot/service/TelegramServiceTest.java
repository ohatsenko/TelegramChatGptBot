package com.smarttek.chatgptbot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.smarttek.chatgptbot.client.TelegramClient;
import com.smarttek.chatgptbot.dto.telegram.TelegramMessageDto;
import com.smarttek.chatgptbot.dto.telegram.TelegramUserDto;
import com.smarttek.chatgptbot.mapper.TelegramMessageMapper;
import com.smarttek.chatgptbot.mapper.TelegramUserMapper;
import com.smarttek.chatgptbot.model.TelegramMessage;
import com.smarttek.chatgptbot.model.TelegramUser;
import com.smarttek.chatgptbot.repository.TelegramMessageRepository;
import com.smarttek.chatgptbot.repository.TelegramUserRepository;
import com.smarttek.chatgptbot.service.impl.TelegramServiceImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
@AutoConfigureMockMvc
public class TelegramServiceTest {
    @MockBean
    private TelegramClient telegramClient;
    @Mock
    private TelegramUserRepository telegramUserRepository;
    @Mock
    private TelegramMessageRepository telegramMessageRepository;
    @Mock
    private TelegramUserMapper telegramUserMapper;
    @Mock
    private TelegramMessageMapper telegramMessageMapper;
    @InjectMocks
    private TelegramServiceImpl telegramService;

    @Test
    @DisplayName("Get all telegram users")
    public void testGetAllUsers_returnLostOfUsers() {
        TelegramUserDto userDto1 = new TelegramUserDto();
        TelegramUserDto userDto2 = new TelegramUserDto();
        when(telegramUserRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(new TelegramUser(), new TelegramUser())));
        when(telegramUserMapper.toDto(any())).thenReturn(userDto1, userDto2);

        List<TelegramUserDto> result = telegramService.getAllUsers(PageRequest.of(0, 10));

        assertEquals(2, result.size());
        verify(telegramUserRepository, times(1)).findAll(any(PageRequest.class));
        verify(telegramUserMapper, times(2)).toDto(any());
    }

    @Test
    @DisplayName("Test get all telegram user messages by chatID")
    public void testGetUserMessages_ReturnListOfMessages() {
        TelegramMessageDto messageDto1 = new TelegramMessageDto();
        TelegramMessageDto messageDto2 = new TelegramMessageDto();
        when(telegramMessageRepository.findAllByTelegramUserId(anyLong(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(new TelegramMessage(),
                        new TelegramMessage())));
        when(telegramMessageMapper.toDto(any())).thenReturn(messageDto1, messageDto2);

        List<TelegramMessageDto> result = telegramService
                .getUserMessages(1L, PageRequest.of(0, 10));

        assertEquals(2, result.size());
        verify(telegramMessageRepository, times(1))
                .findAllByTelegramUserId(anyLong(), any(PageRequest.class));
        verify(telegramMessageMapper, times(2)).toDto(any());
    }
}
