package com.example.students_project.rest;

import com.example.students_project.dto.ImageDto;
import com.example.students_project.dto.ResponseDto;
import com.example.students_project.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class ImageController {
    private final ImageService imageService;

    @Operation(
            summary = "Upload image for a user",
            description = "This endpoint allows authorized users to upload an image for a user.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Image file to be uploaded",
                    required = true,
                    content = @Content(mediaType = "multipart/form-data")
            ),
            parameters = {
                    @Parameter(name = "user_id", description = "ID of the user for whom the image is being uploaded", required = true, example = "123")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Image uploaded successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    @PostMapping
    public ResponseDto<ImageDto> uploadImage(@RequestBody MultipartFile image,
                                             @RequestParam Integer user_id){
        return imageService.uploadImage(image, user_id);
    }

    @Operation(
            summary = "Delete image by ID",
            description = "This endpoint allows authorized users to delete an image by ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the image to delete", required = true, example = "123")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Image deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Image not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @DeleteMapping
    public ResponseDto<ImageDto> deleteImageById(@RequestParam Integer id){
        return imageService.deleteImageById(id);
    }

    @Operation(
            summary = "Get image by ID",
            description = "This endpoint allows authorized users to retrieve an image by ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the image to retrieve", required = true, example = "123")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Image retrieved successfully", content = @Content(mediaType = "application/octet-stream", schema = @Schema(implementation = byte[].class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Image not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseDto<byte[]> getImageById(@PathVariable Integer id){
        return imageService.getImageById(id);
    }
}
