package com.example.students_project.dto;

import com.example.students_project.entity.PracticeSolvedByUsers;
import com.example.students_project.entity.Subject;
import com.example.students_project.security.UserRoles;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto implements UserDetails {
    private Integer id;
    private String firstname;
    private String lastname;
    @NotEmpty(message = "username is empty")
    @NotNull(message = "username  is null")
    private String username;
    @NotEmpty(message = "password is empty")
    @NotNull(message = "password  is null")
    private String password;
    @Email(message = "Email is not valid")
    private String email;
    private String phoneNumber;
    private String role;
    private LocalDateTime createdAt;
    private String description;
    /**
     * Response code for defining type of error:
     * <p>  2 - Deleted </p>
     * <p>  1 - Blocked </p>
     * <p>  0 - OK </p>
     */
    private int activity;
    private List<SubjectDto> subjects;
    private List<PracticeSolvedByUsersDto> practiceSolvedByUsersList;
    private ImageDto imageDto;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return UserRoles.valueOf(role)
                .getAuthorities().stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
