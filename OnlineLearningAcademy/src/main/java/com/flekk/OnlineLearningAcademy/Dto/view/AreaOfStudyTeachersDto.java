package com.flekk.OnlineLearningAcademy.Dto.view;

import com.flekk.OnlineLearningAcademy.Dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AreaOfStudyTeachersDto {
    private String areaOfStudyName;
    private List<UserDto> teachers;
}
