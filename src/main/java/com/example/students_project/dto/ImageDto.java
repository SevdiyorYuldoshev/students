package com.example.students_project.dto;

import com.example.students_project.entity.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDto {
    private Integer id;
    private String url;
    private LocalDateTime createdAt;
    @JsonIgnore
    private UsersDto usersDto;
}
