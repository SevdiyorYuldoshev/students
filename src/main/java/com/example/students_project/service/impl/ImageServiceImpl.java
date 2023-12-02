package com.example.students_project.service.impl;

import com.example.students_project.dto.ImageDto;
import com.example.students_project.dto.ResponseDto;
import com.example.students_project.entity.Image;
import com.example.students_project.entity.Users;
import com.example.students_project.io.FileDownload;
import com.example.students_project.io.FileUpload;
import com.example.students_project.repository.ImageRepository;
import com.example.students_project.repository.UserRepository;
import com.example.students_project.service.ImageService;
import com.example.students_project.service.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.example.students_project.service.validation.StatusCode.*;
import static com.example.students_project.service.validation.StatusMessage.*;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final FileUpload fileUpload;
    private final ImageMapper imageMapper;
    private final ImageRepository imageRepository;
    private final FileDownload fileDownload;
    private final UserRepository userRepository;
    @Override
    public ResponseDto<ImageDto> uploadImage(MultipartFile image, Integer user_id) {
        ImageDto imageDto = imageMapper.toDto(imageRepository.findFirstByUsers_Id(user_id));
        if(imageDto == null) {
            Optional<Users> usersOptional = userRepository.findById(user_id);
            if(usersOptional.isEmpty()) {
                return ResponseDto.<ImageDto>builder()
                        .message("User not found")
                        .code(NOT_FOUND_CODE)
                        .build();
            }
            Users users = usersOptional.get();
            imageDto = ImageDto.builder()
                    .createdAt(LocalDateTime.now())
                    .users(users)
                    .url(fileUpload.uploadFile(image, "image"))
                    .build();
        }
        if(imageDto.getUrl() != null){
            deleteImage(imageDto.getUrl());
        }
        imageDto.setUrl(fileUpload.uploadFile(image, "image"));
        imageDto.setCreatedAt(LocalDateTime.now());
        return ResponseDto.<ImageDto>builder()
                .message(OK)
                .code(OK_CODE)
                .success(true)
                .data(imageMapper.toDto(imageRepository.save(imageMapper.toEntity(imageDto))))
                .build();
    }
    @Override
    public ResponseDto<ImageDto> deleteImageById(Integer id) {
        Optional<Image> byId = imageRepository.findById(id);
        if(byId.isEmpty()){
            return ResponseDto.<ImageDto>builder()
                    .message("No information found on id")
                    .code(NOT_FOUND_CODE)
                    .build();
        }
        Image image = byId.get();
        if(image.getUrl() == null){
            return ResponseDto.<ImageDto>builder()
                    .message("No information found on id")
                    .code(NOT_FOUND_CODE)
                    .build();
        }
        deleteImage(image.getUrl());
        image.setUrl(null);
        return ResponseDto.<ImageDto>builder()
                .message(OK)
                .success(true)
                .code(OK_CODE)
                .data(imageMapper.toDto(imageRepository.save(image)))
                .build();
    }

    @Override
    public ResponseDto<byte[]> getImageById(Integer id) {
        Optional<Image> byId = imageRepository.findById(id);
        if(byId.isEmpty()){
            return ResponseDto.<byte[]>builder()
                    .message("No information found on id")
                    .code(NOT_FOUND_CODE)
                    .build();
        }
        return ResponseDto.<byte[]>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(fileDownload.loadImage(byId.get().getUrl()))
                .build();
    }

    public void deleteImage(String url){
        try {
            Files.deleteIfExists(Path.of(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
