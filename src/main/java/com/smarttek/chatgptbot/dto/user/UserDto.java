package com.smarttek.chatgptbot.dto;

import com.smarttek.chatgptbot.model.Role;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Role.RoleName role;
}
