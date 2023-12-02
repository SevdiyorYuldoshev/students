package com.example.students_project.dto;

import com.example.students_project.entity.Practice;
import com.example.students_project.entity.Users;
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
    private Float grade;
    private Float percentage;
    private String answersFileUrl;
    private Users users;
    private Practice practice;
}
