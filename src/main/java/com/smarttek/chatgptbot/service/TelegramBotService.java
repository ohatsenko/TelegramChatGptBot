package com.smarttek.chatgptbot.service;

public interface TelegramBotService {
    boolean sendResponse(Long chatId, String responseText);
}
