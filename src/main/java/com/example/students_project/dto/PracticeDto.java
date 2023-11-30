package com.example.students_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Practice {
    private Integer id;
    private String practiceName;
    private LocalDateTime practiceDeadline;
    private String practiceFileUrl;
    private Boolean activity;
    private Integer maxGrade;
    private 
}
