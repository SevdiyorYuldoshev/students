package com.example.students_project.service;

import com.example.students_project.dto.FileDto;
import com.example.students_project.dto.ResponseDto;

public interface FileService {
    ResponseDto<String> uploadFile(FileDto fileDto);
}
