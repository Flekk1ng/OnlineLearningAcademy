package com.flekk.OnlineLearningAcademy.Dto;

import jakarta.validation.constraints.NotEmpty;
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
    private String name;
    @NotEmpty
    private String description;
    @NotEmpty
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
