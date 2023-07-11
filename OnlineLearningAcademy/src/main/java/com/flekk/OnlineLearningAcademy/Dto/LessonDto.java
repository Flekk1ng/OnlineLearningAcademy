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
public class LessonDto {
    private Long id;
    @NotEmpty
    @NotBlank
    @Size(min = 3)
    private String topic;
    @NotEmpty
    @NotBlank
    @Size(min = 10)
    private String content;
    @NotEmpty
    private String date;
    @NotEmpty
    @NotBlank
    private String status;
    @NotNull
    private Long courseId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LessonDto lessonDto = (LessonDto) o;
        return Objects.equals(id, lessonDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
