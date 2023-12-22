package com.example.students_project.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class SubjectDto {
    private Integer id;
    @NotEmpty(message = "Name is empty")
    @NotBlank(message = "Name is null")
    private String name;
    private String lectureDocumentUrl;
    private Boolean activity;
    @JsonIgnore
    private UsersDto usersDto;
    @JsonIgnore
    private List<PracticeDto> practices;
    @JsonIgnore
    private List<TestInfoDto> testInfoDtos;
}
