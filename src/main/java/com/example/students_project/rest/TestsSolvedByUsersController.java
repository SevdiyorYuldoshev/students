package com.example.students_project.rest;

import com.example.students_project.dto.ResponseDto;
import com.example.students_project.dto.TestsSolvedByUsersDto;
import com.example.students_project.service.TestsSolvedByUsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solved")
//@SecurityRequirement(name = "Authorization")
@RequiredArgsConstructor
public class TestsSolvedByUsersController {

    private final TestsSolvedByUsersService testsSolvedByUsersService;

    @Operation(
            summary = "Add tests solved by user",
            description = "This endpoint allows authorized users to add information about tests solved by a user.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Tests solved by user object to be added",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TestsSolvedByUsersDto.class))
            ),
            parameters = {
                    @Parameter(name = "userId", description = "ID of the user who solved the tests", required = true, example = "123"),
                    @Parameter(name = "testInfoId", description = "ID of the test information related to the solved tests", required = true, example = "456")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tests solved by user added successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User or test information not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PostMapping
    public ResponseDto<TestsSolvedByUsersDto> addTestsSolvedByUser(@RequestBody TestsSolvedByUsersDto testsSolvedByUsersDto,
                                                              @RequestParam Integer userId,
                                                              @RequestParam Integer testInfoId){
        return testsSolvedByUsersService.addTestsSolvedByUser(testsSolvedByUsersDto, userId, testInfoId);
    }

    @Operation(
            summary = "Get tests solved by user",
            description = "This endpoint allows authorized users to retrieve information about tests solved by a user.",
            parameters = {
                    @Parameter(name = "userId", description = "ID of the user to retrieve solved tests for", required = true, example = "123")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tests solved by user retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User not found or no tests solved by the user", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @GetMapping
    public ResponseDto<TestsSolvedByUsersDto> getTestsSolvedByUser(@RequestParam Integer userId){
        return testsSolvedByUsersService.getTestsSolvedByUser(userId);
    }

    @Operation(
            summary = "Get all tests solved by user by test information ID",
            description = "This endpoint allows authorized users to retrieve all information about tests solved by a user for a specific test information ID.",
            parameters = {
                    @Parameter(name = "testInfoId", description = "ID of the test information to retrieve solved tests for", required = true, example = "456")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "All tests solved by user retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Test information not found or no tests solved by users for the test information", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @GetMapping("/all")
    public ResponseDto<List<TestsSolvedByUsersDto>> getAllTestsSolvedByUserByTestInfoId(@RequestParam Integer testInfoId){
        return testsSolvedByUsersService.getAllTestsSolvedByUserByTestInfoId(testInfoId);
    }

    @PatchMapping
    public ResponseDto<TestsSolvedByUsersDto> updateTestsSolvedByUser(@RequestBody TestsSolvedByUsersDto testsSolvedByUsersDto){
        return testsSolvedByUsersService.updateTestsSolvedByUser(testsSolvedByUsersDto);
    }
}
