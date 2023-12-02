package com.example.students_project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDto {
    @NotBlank(message = "Url is null")
    private String fileType;
    @NotBlank(message = "file is null")
    private MultipartFile file;
}
