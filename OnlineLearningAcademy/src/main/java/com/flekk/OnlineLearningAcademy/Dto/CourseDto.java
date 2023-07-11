package com.flekk.OnlineLearningAcademy.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class CourseDto {
    private Long id;
    @NotEmpty
    @NotBlank
    @Size(min = 3)
    private String name;
    @NotEmpty
    @NotBlank
    @Size(min = 10)
    private String description;
    @NotNull
    private Long areaOfStudyId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDto courseDto = (CourseDto) o;
        return Objects.equals(id, courseDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
