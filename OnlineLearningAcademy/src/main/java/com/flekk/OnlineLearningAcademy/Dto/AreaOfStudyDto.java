package com.flekk.OnlineLearningAcademy.Dto;

import jakarta.validation.constraints.NotBlank;
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
public class AreaOfStudyDto {
    private Long id;
    @NotEmpty
    @NotBlank
    @Size(min = 3)
    private String name;
    @NotEmpty
    @NotBlank
    @Size(min = 10)
    private String description;
}
