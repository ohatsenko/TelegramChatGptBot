package com.smarttek.chatgptbot.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.smarttek.chatgptbot.client.TelegramClient;
import com.smarttek.chatgptbot.model.User;
import com.smarttek.chatgptbot.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private TelegramClient telegramClient;

    @Test
    @DisplayName("Test delete user by ID")
    public void testDeleteUserById() {
        User existingUser = new User();
        existingUser.setId(3L);
        when(userRepository.findById(3L)).thenReturn(Optional.of(existingUser));
        userService.deleteUser(3L);
        verify(userRepository, times(1)).findById(3L);
        verify(userRepository, times(1)).delete(existingUser);
    }

    @Test
    @DisplayName("Test delete non existent user by ID")
    public void testDeleteNonexistentUserById_throwsException() {
        // Arrange
        when(userRepository.findById(3L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteUser(3L);
        });
        verify(userRepository, times(1)).findById(3L);
        verify(userRepository, times(0)).delete(any(User.class));
    }
}
