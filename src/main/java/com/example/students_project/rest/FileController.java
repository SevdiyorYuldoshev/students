package com.example.students_project.rest;

import com.example.students_project.dto.FileDto;
import com.example.students_project.dto.ResponseDto;
import com.example.students_project.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/file-upload")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class FileController {
    private final FileService fileService;

    @Operation(
            summary = "Upload file",
            description = "This endpoint allows authorized users to upload a file.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "File information to be uploaded",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FileDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "File uploaded successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    @PostMapping
    public ResponseDto<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("type") String type){
      return fileService.uploadFile(file, type);
    }

    @Operation(
            summary = "Download file",
            description = "This endpoint allows authorized users to download a file.",
            parameters = {
                    @Parameter(name = "url", description = "URL of the file to download", required = true, example = "example.jpg")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "File downloaded successfully", content = @Content(mediaType = "application/octet-stream")),
                    @ApiResponse(responseCode = "404", description = "File not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    @GetMapping()
    public ResponseEntity<Resource> fileDownload(@RequestParam String url) {
        return fileService.fileDownload(url);
    }
}
