package com.example.students_project.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestInfoDto  {
    private Integer id;
    private String testName;
    private Integer numberOfTests;
    private LocalDateTime testDeadline;
    private boolean activity;
    @JsonIgnore
    private SubjectDto subjectDto;
    @JsonIgnore
    private List<TestsSolvedByUsersDto> testsSolvedByUsersDtos;
    @JsonIgnore
    private List<TestsDto> testsDtos;
}
