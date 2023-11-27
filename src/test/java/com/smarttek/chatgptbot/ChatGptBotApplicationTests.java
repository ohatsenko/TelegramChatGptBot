package com.smarttek.chatgptbot;

import com.smarttek.chatgptbot.client.TelegramClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ChatGptBotApplicationTests {
    @MockBean
    private TelegramClient telegramClient;

    @Test
    void contextLoads() {
    }

}
