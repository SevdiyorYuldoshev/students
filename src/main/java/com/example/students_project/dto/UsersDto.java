package com.example.students_project.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class UserDto {
    private Integer id;
    private String firstname;
    private String lastname;
    @NotEmpty(message = "username is empty")
    @NotNull(message = "username  is null")
    private String username;
    @NotEmpty(message = "password is empty")
    @NotNull(message = "password  is null")
    private String password;
    private String role;
    private String image;
    private LocalDateTime createdAt;
    /**
     * Response code for defining type of error:
     * <p> -2 - Deleted </p>
     * <p> -1 - Blocked </p>
     * <p>  0 - OK </p>
     */
    private int activity;
}
