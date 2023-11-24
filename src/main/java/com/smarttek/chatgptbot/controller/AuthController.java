package com.smarttek.chatgptbot.controller;

import com.smarttek.chatgptbot.dto.user.UserLoginRequestDto;
import com.smarttek.chatgptbot.dto.user.UserLoginResponseDto;
import com.smarttek.chatgptbot.dto.user.UserRegistrationRequestDto;
import com.smarttek.chatgptbot.dto.user.UserRegistrationResponseDto;
import com.smarttek.chatgptbot.exception.RegistrationException;
import com.smarttek.chatgptbot.security.AuthenticationService;
import com.smarttek.chatgptbot.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }

    @Tag(name = "User registration", description = "Endpoint for registration users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/register")
    public UserRegistrationResponseDto register(
            @RequestBody @Valid UserRegistrationRequestDto request
    ) throws RegistrationException {
        return userService.register(request);
    }
}
