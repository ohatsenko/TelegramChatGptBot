package com.smarttek.chatgptbot.dto.telegram;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TelegramMessageDto {
    private Long id;
    private LocalDateTime dateTime;
    private String request;
    private String response;
}
