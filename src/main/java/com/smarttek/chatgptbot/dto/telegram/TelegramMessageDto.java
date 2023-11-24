package com.smarttek.chatgptbot.dto;


import lombok.Data;

@Data
public class TelegramMessageDto {
    private Long id;
    private String question;
    private String answer;
}
