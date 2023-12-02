package com.example.students_project.service.impl;

import com.example.students_project.dto.FileDto;
import com.example.students_project.dto.ResponseDto;
import com.example.students_project.io.FileUpload;
import com.example.students_project.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.students_project.service.validation.StatusCode.*;
import static com.example.students_project.service.validation.StatusMessage.*;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileUpload fileUpload;
    @Override
    public ResponseDto<String> uploadFile(FileDto fileDto) {
        return ResponseDto.<String>builder()
                .message(OK)
                .code(OK_CODE)
                .data(fileUpload.uploadFile(fileDto.getFile(), fileDto.getFileType()))
                .build();
    }
}
