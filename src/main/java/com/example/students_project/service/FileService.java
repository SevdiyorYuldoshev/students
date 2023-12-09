package com.example.students_project.service;

import com.example.students_project.dto.FileDto;
import com.example.students_project.dto.ResponseDto;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    ResponseDto<String> uploadFile(MultipartFile file, String type);

    ResponseEntity<Resource> fileDownload(String url);
}
