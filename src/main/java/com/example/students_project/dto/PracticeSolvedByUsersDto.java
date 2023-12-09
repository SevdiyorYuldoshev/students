package com.example.students_project.dto;

import com.example.students_project.entity.Practice;
import com.example.students_project.entity.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PracticeSolvedByUsersDto {
    private Integer id;
    private float grade;
    private float percentage;
    private String answersFileUrl;
    @JsonIgnore
    private UsersDto usersDto;
    @JsonIgnore
    private PracticeDto practiceDto;
}
