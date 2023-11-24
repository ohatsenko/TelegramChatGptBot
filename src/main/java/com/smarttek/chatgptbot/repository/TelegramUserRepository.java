package com.smarttek.chatgptbot.repository;

import com.smarttek.chatgptbot.model.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {
    TelegramUser findByTelegramId(Long telegramId);
}
