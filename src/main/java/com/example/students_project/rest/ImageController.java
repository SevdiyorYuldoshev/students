package com.example.students_project.rest;

import com.example.students_project.dto.ImageDto;
import com.example.students_project.dto.ResponseDto;
import com.example.students_project.service.ImageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class ImageController {
    private final ImageService imageService;
    @PostMapping
    public ResponseDto<ImageDto> uploadImage(@RequestBody MultipartFile image,
                                             @RequestParam Integer user_id){
        return imageService.uploadImage(image, user_id);
    }
    @DeleteMapping
    public ResponseDto<ImageDto> deleteImageById(@RequestParam Integer id){
        return imageService.deleteImageById(id);
    }
    @GetMapping("/{id}")
    public ResponseDto<byte[]> getImageById(@PathVariable Integer id){
        return imageService.getImageById(id);
    }
}
