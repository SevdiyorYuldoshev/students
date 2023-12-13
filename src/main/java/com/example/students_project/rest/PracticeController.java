package com.example.students_project.rest;

import com.example.students_project.dto.ResponseDto;
import com.example.students_project.dto.PracticeDto;
import com.example.students_project.service.PracticeService;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/practice")
@SecurityRequirement(name = "Authorization")
public class PracticeController {
    private final PracticeService practiceService;

    @Operation(
            summary = "Add practice",
            description = "This endpoint allows authorized users to add a new practice.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Practice information to be added",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PracticeDto.class))
            ),
            parameters = {
                    @Parameter(name = "subjectId", description = "ID of the subject to which the practice belongs", required = true, example = "123")
            },
            responses = {
                    @ApiResponse(responseCode = "201", description = "Practice added successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PostMapping
    public ResponseDto<PracticeDto> addPractice(@Valid @RequestBody PracticeDto practiceDto,
                                                @RequestParam Integer subjectId){
        return practiceService.addPractice(practiceDto, subjectId);
    }

    @Operation(
            summary = "Update practice",
            description = "This endpoint allows authorized users to update practice details.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Practice information with updated details",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PracticeDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Practice updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Practice not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PatchMapping
    public ResponseDto<PracticeDto> updatePractice(@RequestBody PracticeDto practiceDto,
                                                   @RequestParam Integer userId){
        return practiceService.updatePractice(practiceDto, userId);
    }

    @Operation(
            summary = "Get practice by ID",
            description = "This endpoint allows authorized users to retrieve practice details by ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the practice to retrieve", required = true, example = "123")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Practice details retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Practice not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/{id}")
    public ResponseDto<PracticeDto> getPracticeById(@PathVariable Integer id){
        return practiceService.getPracticeById(id);
    }

    @Operation(
            summary = "Get all practices",
            description = "This endpoint allows authorized users to retrieve all practice details.",
            parameters = {
                    @Parameter(name = "page", description = "Page number (zero-based)", required = false, example = "0"),
                    @Parameter(name = "size", description = "Number of items per page", required = false, example = "10")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of all practices retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/all-practice")
    public ResponseDto<Page<EntityModel<PracticeDto>>> getAllPractice(@RequestParam(defaultValue = "0") Integer page,
                                                                    @RequestParam(defaultValue = "10") Integer size){
        return practiceService.getAllPractice(page, size);
    }

    @Operation(
            summary = "Delete practice by ID",
            description = "This endpoint allows authorized users to delete practice details by ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the practice to delete", required = true, example = "123")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Practice deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Practice not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @DeleteMapping
    public ResponseDto<PracticeDto> deletePracticeByID(@RequestParam Integer id){
        return practiceService.deletePracticeById(id);
    }

    @Operation(
            summary = "Get practices by subject ID",
            description = "This endpoint allows authorized users to retrieve practice details by subject ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the subject to retrieve practices", required = true, example = "123"),
                    @Parameter(name = "page", description = "Page number (zero-based)", required = false, example = "0"),
                    @Parameter(name = "size", description = "Number of items per page", required = false, example = "10")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of practices retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Subject not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/get-by-subject-id/{id}")
    public ResponseDto<Page<EntityModel<PracticeDto>>> getBySubjectId(@PathVariable Integer id,
                                                                      @RequestParam(defaultValue = "0") Integer page,
                                                                      @RequestParam(defaultValue = "10") Integer size) {
        return practiceService.getBySubjectId(id, page, size);
    }
}
