package com.example.students_project.service.impl;

import com.example.students_project.dto.FileDto;
import com.example.students_project.dto.ResponseDto;
import com.example.students_project.io.FileDownload;
import com.example.students_project.io.FileUpload;
import com.example.students_project.service.FileService;
import jdk.jfr.consumer.RecordedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.example.students_project.service.validation.StatusCode.*;
import static com.example.students_project.service.validation.StatusMessage.*;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileUpload fileUpload;
    private final FileDownload fileDownload;
    @Override
    public ResponseDto<String> uploadFile(MultipartFile file, String type) {
        return ResponseDto.<String>builder()
                .message(OK)
                .code(OK_CODE)
                .success(true)
                .data(fileUpload.uploadFile(file, type))
                .build();
    }

    @Override
    public ResponseEntity<Resource> fileDownload(String url) {

        ByteArrayResource resource = new ByteArrayResource(fileDownload.loadImage(url));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + url.substring(url.lastIndexOf('/')) + "\"")
                .body(resource);
    }
}
