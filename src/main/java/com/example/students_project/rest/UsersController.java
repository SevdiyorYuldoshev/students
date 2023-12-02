package com.example.students_project.rest;

import com.example.students_project.dto.LoginDto;
import com.example.students_project.dto.ResponseDto;
import com.example.students_project.dto.UsersDto;
import com.example.students_project.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;

@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "Authorization")
public class UsersController {
    @Autowired
    private UsersService userService;
    @Operation(
            summary = "Add a new user",
            description = "This endpoint allows authorized users to add a new user.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User object to be added",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsersDto.class))
            ),
            parameters = {
                    @Parameter(name = "usersDto", description = "User object to be added", required = true, schema = @Schema(implementation = UsersDto.class))
            },
            responses = {
                    @ApiResponse(responseCode = "201", description = "User added successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('USER','ADMIN', 'SUPER_ADMIN')")
    @PostMapping
    public ResponseDto<UsersDto> addUser(@Valid @RequestBody UsersDto usersDto){
        return userService.addUser(usersDto);
    }

    @Operation(
            summary = "Update user details",
            description = "This endpoint allows authorized users to update user details.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User object with updated details",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsersDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('USER','ADMIN', 'SUPER_ADMIN')")
    @PatchMapping
    public ResponseDto<UsersDto> updateUser(@RequestBody UsersDto usersDto){
        return userService.updateUser(usersDto);
    }

    @Operation(
            summary = "Get user by ID",
            description = "This endpoint allows authorized users to retrieve user details by ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the user to retrieve", required = true, example = "123")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "User details retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PreAuthorize("hasAnyRole('USER','ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/{id}")
    public ResponseDto<UsersDto> getUserById(@PathVariable Integer id){
        return userService.getUserById(id);
    }
    @Operation(
            summary = "Get all users",
            description = "This endpoint allows authorized users to retrieve a paginated list of all users.",
            parameters = {
                    @Parameter(name = "page", description = "Page number (zero-based)", required = false, example = "0"),
                    @Parameter(name = "size", description = "Number of items per page", required = false, example = "10")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of users retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @GetMapping("/all-user")
    public ResponseDto<Page<EntityModel<UsersDto>>> getAllUsers(@RequestParam(defaultValue = "0") Integer page,
                                                                @RequestParam(defaultValue = "10") Integer size){
        return userService.getAllUsers(page, size);
    }
    @Operation(
            summary = "Delete user by ID",
            description = "This endpoint allows authorized users to delete a user by ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the user to delete", required = true, example = "123")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "User deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @DeleteMapping
    public ResponseDto<UsersDto> deleteUser(@RequestParam Integer id){
        return userService.deleteUser(id);
    }


    @Operation(
            summary = "User login",
            description = "This endpoint allows users to log in.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Login information",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User logged in successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PostMapping("login")
    public ResponseDto<String> loginUser(@RequestBody LoginDto loginDto) throws NoSuchMethodException {
        Link link = Link.of("/subject/", "subject-list");
        ResponseDto<String> response = userService.loginUser(loginDto);
        response.add(link);

        Method getUserById = UsersController.class
                .getDeclaredMethod("getUserById", Integer.class);

        return response;
    }

    @Operation(
            summary = "Update user password",
            description = "This endpoint allows authorized users to update their password.",
            parameters = {
                    @Parameter(name = "username", description = "Username of the user", required = true),
                    @Parameter(name = "password", description = "Current password", required = true),
                    @Parameter(name = "newPassword", description = "New password", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Password updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
            }
    )
    @PatchMapping("/update-password")
    public ResponseDto<UsersDto> updatePassword(@RequestParam String username,
                                             @RequestParam String password,
                                             @RequestParam String newPassword){
        return userService.updatePassword(username, password, newPassword);
    }
}
