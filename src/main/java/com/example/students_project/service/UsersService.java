package com.example.students_project.service;


import com.example.students_project.dto.LoginDto;
import com.example.students_project.dto.ResponseDto;
import com.example.students_project.dto.UsersDto;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface UsersService extends UserDetailsService {

    ResponseDto<UsersDto> addUser(UsersDto usersDto);

    ResponseDto<UsersDto> updateUser(UsersDto usersDto);

    ResponseDto<UsersDto> getUserById(Integer id);

    ResponseDto<Page<EntityModel<UsersDto>>> getAllUsers(Integer page, Integer size);

    ResponseDto<UsersDto> deleteUser(Integer id);

    ResponseDto<String> loginUser(LoginDto loginDto);

    ResponseDto<UsersDto> updatePassword(String username, String password, String newPassword);
}
