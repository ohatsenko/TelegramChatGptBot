package com.smarttek.chatgptbot.service.impl;

import com.smarttek.chatgptbot.model.TelegramMessage;
import com.smarttek.chatgptbot.model.TelegramUser;
import com.smarttek.chatgptbot.repository.TelegramMessageRepository;
import com.smarttek.chatgptbot.repository.TelegramUserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Service
@RequiredArgsConstructor
public class TelegramBostServiceImpl extends TelegramLongPollingBot {
    private final TelegramUserRepository telegramUserRepository;
    private final TelegramMessageRepository telegramMessageRepository;

    private final String BOT_NAME = "your_bot_name";
    private final String BOT_TOKEN = "your_bot_token";

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();

            // Получение пользователя по telegramId
            TelegramUser telegramUser = telegramUserRepository.findByTelegramId(chatId);
            if (telegramUser == null) {
                // Создание нового пользователя, если он не найден
                telegramUser = new TelegramUser();
                telegramUser.setTelegramId(chatId);
                telegramUser = telegramUserRepository.save(telegramUser);
            }

            // Получение ответа от ChatGPT
   //         String responseText = chatGptService.getResponse(messageText);
            String responseText = "GPT Answer";

            // Создание нового сообщения
            TelegramMessage telegramMessage = new TelegramMessage();
            telegramMessage.setQuestion(messageText);
            telegramMessage.setAnswer(responseText);
            telegramMessage.setTelegramUser(telegramUser);

            // Сохранение сообщения в базе данных
            telegramMessageRepository.save(telegramMessage);

            // Отправка ответа пользователю
            SendMessage message = new SendMessage();
            message.setChatId(chatId.toString());
            message.setText(responseText);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @PostConstruct
    public void startBot() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
