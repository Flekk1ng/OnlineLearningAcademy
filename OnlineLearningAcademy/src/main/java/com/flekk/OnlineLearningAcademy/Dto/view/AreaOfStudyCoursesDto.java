package com.flekk.OnlineLearningAcademy.Dto.view;

import com.flekk.OnlineLearningAcademy.Dto.CourseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AreaOfStudyCoursesDto {
    private String areaOfStudyName;
    private List<CourseDto> courses;
}
