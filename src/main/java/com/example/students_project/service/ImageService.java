package com.example.students_project.service;

import com.example.students_project.dto.ImageDto;
import com.example.students_project.dto.ResponseDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ResponseDto<ImageDto> uploadImage(MultipartFile image, Integer user_id);

    ResponseDto<ImageDto> deleteImageById(Integer id);

    ResponseDto<byte[]> getImageById(Integer id);
}
