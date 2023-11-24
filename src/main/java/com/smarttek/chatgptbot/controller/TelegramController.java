package com.smarttek.chatgptbot.controller;

import com.smarttek.chatgptbot.dto.TelegramMessageDto;
import com.smarttek.chatgptbot.dto.TelegramUserDto;
import com.smarttek.chatgptbot.service.TelegramUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "Admin management", description = "Endpoints for managing admins")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TelegramUserController {
    private final TelegramUserService telegramUserService;

    @GetMapping("/users")
    public List<TelegramUserDto> getAllTelegramUsers(Pageable pageable) {
        return telegramUserService.getAllUsers(pageable);
    }

    @GetMapping("/users/{id}/messages")
    public List<TelegramMessageDto> getUserMessages(@PathVariable Long id, Pageable pageable) {
        return telegramUserService.getUserMessages(id, pageable);
    }
}
