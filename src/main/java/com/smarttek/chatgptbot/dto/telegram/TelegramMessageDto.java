package com.smarttek.chatgptbot.dto.telegram;

import lombok.Data;

@Data
public class TelegramMessageDto {
    private Long id;
    private String request;
    private String response;
}
