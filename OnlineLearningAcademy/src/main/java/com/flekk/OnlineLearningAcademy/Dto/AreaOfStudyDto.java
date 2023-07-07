package com.flekk.OnlineLearningAcademy.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AreaOfStudyDto {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
}
