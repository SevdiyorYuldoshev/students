package com.example.students_project.rest;

import com.example.students_project.dto.ResponseDto;
import com.example.students_project.dto.TestInfoDto;
import com.example.students_project.dto.UsersDto;
import com.example.students_project.service.TestsInfoService;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test-info")
@SecurityRequirement(name = "Authorization")
@RequiredArgsConstructor
public class TestInfoController {

    private final TestsInfoService testsInfoService;

    @Operation(
            summary = "Add test information",
            description = "This endpoint allows authorized users to add test information.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Test information object to be added",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TestInfoDto.class))
            ),
            parameters = {
                    @Parameter(name = "subjectId", description = "ID of the subject related to the test information", required = true, example = "123")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Test information added successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Subject not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PostMapping
    public ResponseDto<TestInfoDto> addTestsInfo(@Valid @RequestBody TestInfoDto testInfoDto,
                                            @RequestParam Integer subjectId){
        return testsInfoService.addTestsInfo(testInfoDto, subjectId);
    }

    @Operation(
            summary = "Update test information",
            description = "This endpoint allows authorized users to update test information.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Test information object with updated details",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TestInfoDto.class))
            ),
            parameters = {
                    @Parameter(name = "userId", description = "ID of the user updating the test information", required = true, example = "123")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Test information updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Test information not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PatchMapping
    public ResponseDto<TestInfoDto> updateTestsInfo(@RequestBody TestInfoDto testInfoDto,
                                               @RequestParam Integer userId){
        return testsInfoService.updateTestsInfo(testInfoDto);
    }

    @Operation(
            summary = "Get test information by ID",
            description = "This endpoint allows authorized users to retrieve test information by ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the test information to retrieve", required = true, example = "456")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Test information retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Test information not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseDto<TestInfoDto> getTestsInfoById(@PathVariable Integer id){
        return testsInfoService.getTestInfoById(id);
    }

    @Operation(
            summary = "Get all test information",
            description = "This endpoint allows authorized users to retrieve all test information.",
            parameters = {
                    @Parameter(name = "page", description = "Page number (starting from 0)", example = "0"),
                    @Parameter(name = "size", description = "Number of items per page", example = "10")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Test information retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @GetMapping("/all-test-info")
    public ResponseDto<Page<EntityModel<TestInfoDto>>> getAllTestsInfo(@RequestParam(defaultValue = "0") Integer page,
                                                                @RequestParam(defaultValue = "10") Integer size){
        return testsInfoService.getAllTestsInfo(page, size);
    }

    @Operation(
            summary = "Delete test information",
            description = "This endpoint allows authorized users to delete test information.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the test information to delete", required = true, example = "789")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Test information deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Test information not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @DeleteMapping
    public ResponseDto<TestInfoDto> deleteTestsInfo(@RequestParam Integer id){
        return testsInfoService.deleteTestsInfo(id);
    }
}
