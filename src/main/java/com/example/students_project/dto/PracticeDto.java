package com.example.students_project.dto;

import com.example.students_project.entity.PracticeSolvedByUsers;
import com.example.students_project.entity.Subject;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PracticeDto {
    private Integer id;
    @NotBlank(message = "Practice name is blank")
    @Min(5)
    @Max(200)
    private String practiceName;
    @NotBlank(message = "Practice deadline is blank")
    private LocalDateTime practiceDeadline;
    private String practiceFileUrl;
    private Boolean activity;
    private Integer maxGrade;
    private Subject subject;
    private List<PracticeSolvedByUsers> practiceSolvedByUsersList;
}
