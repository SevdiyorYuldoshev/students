package com.example.students_project.rest;

import com.example.students_project.dto.FileDto;
import com.example.students_project.dto.ResponseDto;
import com.example.students_project.service.FileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file-upload")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class FileController {
    private final FileService fileService;
    @PostMapping
    public ResponseDto<String> uploadFile(@Valid @RequestBody FileDto fileDto){
      return fileService.uploadFile(fileDto);
    }
}
