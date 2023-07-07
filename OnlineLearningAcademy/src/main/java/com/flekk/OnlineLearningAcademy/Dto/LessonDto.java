package com.flekk.OnlineLearningAcademy.Dto;

import com.flekk.OnlineLearningAcademy.model.Lesson;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    private String topic;
    @NotEmpty
    private String content;
    @NotEmpty
    private String date;
    @NotEmpty
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
