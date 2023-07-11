package com.flekk.OnlineLearningAcademy.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
    private Long id;
    @NotEmpty
    @NotBlank
    @Size(min = 3)
    private String firstName;
    @NotEmpty
    @NotBlank
    @Size(min = 3)
    private String lastName;
    private String email;
    @NotEmpty
    @NotBlank
    private String role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

