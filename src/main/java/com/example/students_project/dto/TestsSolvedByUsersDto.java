package com.example.students_project.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestsSolvedByUsersDto {
    private Integer id;
    private int numberOfCorrect;
    private float percentage;
    @JsonIgnore
    private TestInfoDto testInfoDto;
    @JsonIgnore
    private UsersDto usersDto;
}
