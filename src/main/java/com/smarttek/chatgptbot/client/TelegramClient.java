package com.smarttek.chatgptbot.client;

public interface TelegramClient {
    boolean sendResponse(Long chatId, String responseText);
}
