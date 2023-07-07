package com.flekk.OnlineLearningAcademy.Dto.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseAttendanceDto {
    private String courseName;
    private Long attendanceCount;
}