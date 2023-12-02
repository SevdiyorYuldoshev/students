package com.example.students_project.service.mapper;

import com.example.students_project.dto.UsersDto;
import com.example.students_project.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsersMapper extends CommonMapper<UsersDto, Users> {
}
