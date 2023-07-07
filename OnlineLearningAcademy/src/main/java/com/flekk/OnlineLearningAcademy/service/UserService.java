package com.flekk.OnlineLearningAcademy.service;

import com.flekk.OnlineLearningAcademy.Dto.view.RegisterUserDto;
import com.flekk.OnlineLearningAcademy.Dto.UserDto;

import java.util.List;
import java.util.Optional;


public interface UserService {
    void createUser(RegisterUserDto registerUserDto);
    void updateUser(UserDto userDto);
    void deleteById(Long id);
    Optional<UserDto> findUserByEmail(String email);

    List<UserDto> findAllUsers();
    UserDto findById(Long id);


}
