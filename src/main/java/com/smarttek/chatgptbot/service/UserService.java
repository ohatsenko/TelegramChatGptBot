package com.smarttek.chatgptbot.service;

import com.smarttek.chatgptbot.dto.user.UserDto;
import com.smarttek.chatgptbot.dto.user.UserRegistrationRequestDto;
import com.smarttek.chatgptbot.dto.user.UserRegistrationResponseDto;
import com.smarttek.chatgptbot.exception.RegistrationException;
import com.smarttek.chatgptbot.model.User;
import java.util.Set;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserRegistrationResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException;

    User getAuthenticatedUser();

    Set<UserDto> getAllUsers(Pageable pageable);

    void deleteUser(Long id);
}
