package com.flekk.OnlineLearningAcademy.service.impl;

import com.flekk.OnlineLearningAcademy.Dto.view.RegisterUserDto;
import com.flekk.OnlineLearningAcademy.Dto.UserDto;
import com.flekk.OnlineLearningAcademy.model.Role;
import com.flekk.OnlineLearningAcademy.model.User;
import com.flekk.OnlineLearningAcademy.repository.UserRepository;
import com.flekk.OnlineLearningAcademy.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("User not found"));
        return convertToDto(user);
    }

    @Override
    public void createUser(RegisterUserDto registerUserDto) {
        logger.info("Creating user {}", registerUserDto.getEmail());
        User user = new User();
        user.setFirstName(registerUserDto.getFirstName());
        user.setLastName(registerUserDto.getLastName());
        user.setEmail(registerUserDto.getEmail());
        user.setRole(Role.STUDENT);
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        userRepository.save(user);
        logger.info("User {} created successfully", registerUserDto.getEmail());
    }

    @Override
    public void updateUser(UserDto userDto) {
        logger.info("Updating user {}", userDto.getEmail());
        try {
            User user = userRepository.findById(userDto.getId()).orElseThrow(()
                    -> new EntityNotFoundException("User not found"));
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setRole(Role.valueOf(userDto.getRole()));
            userRepository.save(user);
            logger.info("User {} updated successfully", userDto.getEmail());
        } catch (Exception e) {
            logger.error("Error updating user {}: {}", userDto.getEmail(), e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting user {}", id);
        try {
            userRepository.deleteById(id);
            logger.info("User {} deleted successfully", id);
        } catch (Exception e) {
            logger.error("Error deleting user {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<UserDto> findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::convertToDto);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private UserDto convertToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().name());
        return userDto;
    }
}
