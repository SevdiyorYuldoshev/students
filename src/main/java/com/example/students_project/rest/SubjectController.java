package com.example.students_project.rest;

import com.example.students_project.dto.ResponseDto;
import com.example.students_project.dto.SubjectDto;
import com.example.students_project.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class SubjectController {
    private final SubjectService subjectService;
    @Operation(
            summary = "Add a new subject",
            description = "This endpoint allows authorized users to add a new subject.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Subject information to be added",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SubjectDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Subject added successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PostMapping
    public ResponseDto<SubjectDto> addSubject(@Valid @RequestBody SubjectDto subjectDto,
                                              @RequestParam Integer userId){
        return subjectService.addSubject(subjectDto, userId);
    }

    @Operation(
            summary = "Update subject details",
            description = "This endpoint allows authorized users to update subject details.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Subject object with updated details",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SubjectDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Subject updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Subject not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PatchMapping
    public ResponseDto<SubjectDto> updateSubject(@RequestBody SubjectDto subjectDto,
                                                 @RequestParam Integer userId){
        return subjectService.updateSubject(subjectDto, userId);
    }

    @Operation(
            summary = "Get subject by ID",
            description = "This endpoint allows authorized users to retrieve subject details by ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the subject to retrieve", required = true, example = "123")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Subject details retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Subject not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/{id}")
    public ResponseDto<SubjectDto> getSubjectById(@PathVariable Integer id){
        return subjectService.getSubjectById(id);
    }

    @Operation(
            summary = "Get all subjects",
            description = "This endpoint allows authorized users to retrieve a paginated list of all subjects.",
            parameters = {
                    @Parameter(name = "page", description = "Page number (zero-based)", required = false, example = "0"),
                    @Parameter(name = "size", description = "Number of items per page", required = false, example = "10")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of subjects retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/all")
    public ResponseDto<Page<EntityModel<SubjectDto>>> getAllSubject(@RequestParam(defaultValue = "0") Integer page,
                                                                    @RequestParam(defaultValue = "10") Integer size){
        return subjectService.getAllSubject(page, size);
    }

    @Operation(
            summary = "Delete subject by ID",
            description = "This endpoint allows authorized users to delete a subject by ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the subject to delete", required = true, example = "123")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Subject deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Subject not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @DeleteMapping
    public ResponseDto<SubjectDto> deleteSubjectByID(@RequestParam Integer id){
        return subjectService.deleteSubjectById(id);
    }

    @Operation(
            summary = "Get subjects by user ID",
            description = "This endpoint allows authorized users to retrieve subjects associated with a specific user.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the user", required = true, example = "123"),
                    @Parameter(name = "page", description = "Page number (zero-based)", required = false, example = "0"),
                    @Parameter(name = "size", description = "Number of items per page", required = false, example = "10")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of subjects retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/by-user/{id}")
    public ResponseDto<Page<EntityModel<SubjectDto>>> getSubjectByUserId(@PathVariable Integer id,
                                                                         @RequestParam(defaultValue = "0") Integer page,
                                                                         @RequestParam(defaultValue = "10") Integer size){
        return subjectService.getSubjectByUserId(id, page, size);
    }
}
