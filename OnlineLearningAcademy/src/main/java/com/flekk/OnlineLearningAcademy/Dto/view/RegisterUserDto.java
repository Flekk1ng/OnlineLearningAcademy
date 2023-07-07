package com.flekk.OnlineLearningAcademy.Dto.view;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {
    private Long id;
    @NotEmpty
    @Size(min = 3)
    private String firstName;
    @NotEmpty
    @Size(min = 3)
    private String lastName;
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password should not be empty")
    private String password;
    private String role;
}
