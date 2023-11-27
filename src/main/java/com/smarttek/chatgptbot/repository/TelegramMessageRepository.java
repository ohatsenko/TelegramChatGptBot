package com.smarttek.chatgptbot.repository;

import com.smarttek.chatgptbot.model.TelegramMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelegramMessageRepository extends JpaRepository<TelegramMessage, Long> {
    Page<TelegramMessage> findAllByTelegramUserId(Long id, Pageable pageable);
}
