package com.example.students_project.rest;

import com.example.students_project.dto.ResponseDto;
import com.example.students_project.dto.TestsDto;
import com.example.students_project.service.TestsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tests")
@RequiredArgsConstructor
public class TestsController {
    private final TestsService testsService;

    @Operation(
            summary = "Add tests",
            description = "This endpoint allows authorized users to add a list of tests to a test information.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "List of test objects to be added",
                    required = true,
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TestsDto.class)))
            ),
            parameters = {
                    @Parameter(name = "testInfoId", description = "ID of the test information to associate the tests with", required = true, example = "123")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tests added successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Test information not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PostMapping("/add-tests")
    public ResponseDto<List<TestsDto>> addTests(@RequestBody List<TestsDto> testsDtoList,
                                                @RequestParam Integer testInfoId){
        return testsService.addTests(testsDtoList, testInfoId);
    }

    @Operation(
            summary = "Get tests by test information ID",
            description = "This endpoint allows authorized users to retrieve a list of tests by test information ID.",
            parameters = {
                    @Parameter(name = "testInfoId", description = "ID of the test information to retrieve tests for", required = true, example = "123")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tests retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Test information not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @GetMapping("/get-tests")
    public ResponseDto<List<TestsDto>> getTestsByTestInfoId(@RequestParam Integer testInfoId){
        return testsService.getTestsByTestInfoId(testInfoId);
    }

    @Operation(
            summary = "Delete tests by test information ID",
            description = "This endpoint allows authorized users to delete tests by test information ID.",
            parameters = {
                    @Parameter(name = "testInfoId", description = "ID of the test information to delete tests for", required = true, example = "123")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tests deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Test information not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @DeleteMapping("/delete-tests")
    public ResponseDto<List<TestsDto>> deleteTestsByTestInfoId(@RequestParam Integer testInfoId){
        return testsService.deleteTestsByTestInfoId(testInfoId);
    }
}
