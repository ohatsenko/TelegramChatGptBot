package com.smarttek.chatgptbot.service.impl;

import com.smarttek.chatgptbot.dto.user.UserDto;
import com.smarttek.chatgptbot.dto.user.UserRegistrationRequestDto;
import com.smarttek.chatgptbot.dto.user.UserRegistrationResponseDto;
import com.smarttek.chatgptbot.exception.EntityNotFoundException;
import com.smarttek.chatgptbot.exception.RegistrationException;
import com.smarttek.chatgptbot.mapper.UserMapper;
import com.smarttek.chatgptbot.model.Role;
import com.smarttek.chatgptbot.model.User;
import com.smarttek.chatgptbot.repository.RoleRepository;
import com.smarttek.chatgptbot.repository.UserRepository;
import com.smarttek.chatgptbot.service.UserService;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    @Override
    public UserRegistrationResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RegistrationException("User with such email: "
                    + request.getEmail() + " is already exists");
        }

        User user = userMapper.toModel(request);
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        Set<Role> roles = new HashSet<>();
        Role defaultRole = roleRepository.getByRoleName(Role.RoleName.ROLE_USER);
        roles.add(defaultRole);
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find user with email " + authentication.getName()));
    }

    @Override
    public Set<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .stream()
                .filter(user -> user.getId() != 1)
                .map(userMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("User with id " + id + " not found"));
        userRepository.delete(user);
    }
}
