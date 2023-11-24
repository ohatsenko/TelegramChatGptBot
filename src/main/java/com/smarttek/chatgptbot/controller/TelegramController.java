package com.smarttek.chatgptbot.controller;

import com.smarttek.chatgptbot.dto.telegram.TelegramMessageDto;
import com.smarttek.chatgptbot.dto.telegram.TelegramUserDto;
import com.smarttek.chatgptbot.service.TelegramBotService;
import com.smarttek.chatgptbot.service.TelegramService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Telegram management", description = "Endpoints for managing telegram messages")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TelegramController {
    private final TelegramService telegramService;
    private final TelegramBotService telegramBotService;

    @Tag(name = "Get all telegram users", description = "Get all telegram users")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
    @GetMapping("/users")
    public List<TelegramUserDto> getAllTelegramUsers(Pageable pageable) {
        return telegramService.getAllUsers(pageable);
    }

    @Tag(name = "Get messages", description = "Get messages by user id")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
    @GetMapping("/users/{id}/messages")
    public List<TelegramMessageDto> getUserMessages(@PathVariable Long id, Pageable pageable) {
        return telegramService.getUserMessages(id, pageable);
    }

    @Tag(name = "Send message", description = "Send message by chat id")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{chatId}")
    public String sendMessageByChatId(@PathVariable Long chatId, @RequestParam String message) {
        return String.valueOf(telegramBotService.sendResponse(chatId, message));
    }
}
