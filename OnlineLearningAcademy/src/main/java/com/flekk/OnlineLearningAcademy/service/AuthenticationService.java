package com.flekk.OnlineLearningAcademy.service;

import com.flekk.OnlineLearningAcademy.Dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserService userService;

    @Autowired
    public AuthenticationService(UserService userService) {
        this.userService = userService;
    }

    public Optional<UserDto> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            return userService.findUserByEmail(email);
        }
        return Optional.empty();
    }
}
