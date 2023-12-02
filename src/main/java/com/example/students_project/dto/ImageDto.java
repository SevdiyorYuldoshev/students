package com.example.students_project.dto;

import com.example.students_project.entity.Users;
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
    private Users users;
}
