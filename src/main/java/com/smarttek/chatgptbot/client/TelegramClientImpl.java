package com.smarttek.chatgptbot.client;

import com.smarttek.chatgptbot.exception.TelegramException;
import com.smarttek.chatgptbot.model.TelegramMessage;
import com.smarttek.chatgptbot.model.TelegramUser;
import com.smarttek.chatgptbot.repository.TelegramMessageRepository;
import com.smarttek.chatgptbot.repository.TelegramUserRepository;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Service
@RequiredArgsConstructor
public class TelegramClientImpl extends TelegramLongPollingBot implements TelegramClient {
    private final TelegramUserRepository telegramUserRepository;
    private final TelegramMessageRepository telegramMessageRepository;
    private final ChatGptClient chatGptClient;

    @Value("${telegram.name}")
    private String botName;
    @Value("${telegram.token}")
    private String botToken;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();

            TelegramUser telegramUser = telegramUserRepository.findByTelegramId(chatId);
            if (telegramUser == null) {
                telegramUser = new TelegramUser();
                telegramUser.setTelegramId(chatId);
                telegramUser = telegramUserRepository.save(telegramUser);
            }

            String responseText = chatGptClient.getResponse(messageText);

            TelegramMessage telegramMessage = new TelegramMessage();
            telegramMessage.setDateTime(LocalDateTime.now());
            telegramMessage.setRequest(messageText);
            telegramMessage.setResponse(responseText);
            telegramMessage.setTelegramUser(telegramUser);

            telegramMessageRepository.save(telegramMessage);

            sendResponse(chatId, responseText);
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @PostConstruct
    public void startBot() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            throw new TelegramException("Can't launch telegram bot");
        }
    }

    @Override
    public boolean sendResponse(Long chatId, String responseText) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(responseText);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new TelegramException("Can't send a message "
                    + responseText + " to chat: " + chatId);
        }
        return true;
    }
}
