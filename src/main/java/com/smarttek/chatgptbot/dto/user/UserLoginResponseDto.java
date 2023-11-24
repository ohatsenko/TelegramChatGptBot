package com.smarttek.chatgptbot.dto;

import lombok.Data;

@Data
public class UserLoginResponseDto {
    private String email;
    private String password;
    private String repeatPassword;
    private String firstName;
    private String lastName;
    private final String token;
}
