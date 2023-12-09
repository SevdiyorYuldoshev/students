package com.example.students_project.rest;

import com.example.students_project.dto.PracticeSolvedByUsersDto;
import com.example.students_project.dto.ResponseDto;
import com.example.students_project.service.PracticeSolvedByUsersService;
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
@RequestMapping("/practice-solved-by-users")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class PracticeSolvedByUsersController {
    private final PracticeSolvedByUsersService practiceSolvedByUsersService;
    @Operation(
            summary = "Add practice solved by users",
            description = "This endpoint allows authorized users to add practice details solved by users.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Practice solved by users information to be added",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PracticeSolvedByUsersDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Practice solved by users added successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    @PostMapping
    public ResponseDto<PracticeSolvedByUsersDto> addPracticeSolvedByUsers(@Valid @RequestBody PracticeSolvedByUsersDto practiceSolvedByUsersDto,
                                                                          @RequestParam Integer userId,
                                                                          @RequestParam Integer practiceId){
        return practiceSolvedByUsersService.addPracticeSolvedByUsers(practiceSolvedByUsersDto, userId, practiceId);
    }
    @Operation(
            summary = "Update practice solved by users",
            description = "This endpoint allows authorized users to update practice details solved by users.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Practice solved by users information with updated details",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PracticeSolvedByUsersDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Practice solved by users updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Practice solved by users not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    @PatchMapping
    public ResponseDto<PracticeSolvedByUsersDto> updatePracticeSolvedByUsers(@RequestBody PracticeSolvedByUsersDto practiceSolvedByUsersDto){
        return practiceSolvedByUsersService.updatePracticeSolvedByUsers(practiceSolvedByUsersDto);
    }

    @Operation(
            summary = "Get practice solved by users by ID",
            description = "This endpoint allows authorized users to retrieve practice details solved by users by ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the practice solved by users to retrieve", required = true, example = "123")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Practice solved by users details retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Practice solved by users not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/{id}")
    public ResponseDto<PracticeSolvedByUsersDto> getPracticeSolvedByUsersById(@PathVariable Integer id){
        return practiceSolvedByUsersService.getPracticeSolvedByUsersById(id);
    }

    @Operation(
            summary = "Get practice solved by users by practice ID",
            description = "This endpoint allows authorized users to retrieve practice details solved by users by practice ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the practice to retrieve practice solved by users", required = true, example = "123"),
                    @Parameter(name = "page", description = "Page number (zero-based)", required = false, example = "0"),
                    @Parameter(name = "size", description = "Number of items per page", required = false, example = "10")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of practice solved by users retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Practice not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/by-practice-id")
    public ResponseDto<Page<EntityModel<PracticeSolvedByUsersDto>>> getByPracticeId(@RequestParam Integer id,
                                                                                 @RequestParam(defaultValue = "0") Integer page,
                                                                                 @RequestParam(defaultValue = "10") Integer size){
        return practiceSolvedByUsersService.getByPracticeId(id, page, size);
    }

    @Operation(
            summary = "Get practice solved by users by user ID",
            description = "This endpoint allows authorized users to retrieve practice details solved by users by user ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the user to retrieve practice solved by users", required = true, example = "123"),
                    @Parameter(name = "page", description = "Page number (zero-based)", required = false, example = "0"),
                    @Parameter(name = "size", description = "Number of items per page", required = false, example = "10")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of practice solved by users retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/by-user-id")
    public ResponseDto<Page<EntityModel<PracticeSolvedByUsersDto>>> getByUserId(@RequestParam Integer id,
                                                                                                    @RequestParam(defaultValue = "0") Integer page,
                                                                                                    @RequestParam(defaultValue = "10") Integer size){
        return practiceSolvedByUsersService.getByUserId(id, page, size);
    }

    @Operation(
            summary = "Get all practice solved by users",
            description = "This endpoint allows authorized users to retrieve all practice details solved by users.",
            parameters = {
                    @Parameter(name = "page", description = "Page number (zero-based)", required = false, example = "0"),
                    @Parameter(name = "size", description = "Number of items per page", required = false, example = "10")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of all practice solved by users retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/all-solved-practice")
    public ResponseDto<Page<EntityModel<PracticeSolvedByUsersDto>>> getAllPracticeSolvedByUsers(@RequestParam(defaultValue = "0") Integer page,
                                                                      @RequestParam(defaultValue = "10") Integer size){
        return practiceSolvedByUsersService.getAllPracticeSolvedByUsers(page, size);
    }

    @Operation(
            summary = "Delete practice solution by ID",
            description = "This endpoint allows authorized users to delete a practice solution by ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the practice solution to delete", required = true, example = "123")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Practice solution deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Practice solution not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @DeleteMapping
    public ResponseDto<PracticeSolvedByUsersDto> deletePracticeSolvedByUsersByID(@RequestParam Integer id){
        return practiceSolvedByUsersService.deletePracticeSolvedByUsersByID(id);
    }

    @Operation(
            summary = "Make or update grade for practice solved by users",
            description = "This endpoint allows authorized users to make or update the grade for a practice solved by users.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the practice solved by users", required = true, example = "123"),
                    @Parameter(name = "grade", description = "Grade to be assigned or updated", required = true, example = "90.5")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Grade assigned or updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Practice solved by users not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PatchMapping("/make-grade")
    public ResponseDto<PracticeSolvedByUsersDto> makeGrade(@RequestParam Integer id,
                                                           @RequestParam Float grade){
        return practiceSolvedByUsersService.makeGrade(id,grade);
    }
}
