package com.example.students_project.service.mapper;

import com.example.students_project.dto.TestsSolvedByUsersDto;
import com.example.students_project.entity.TestsSolvedByUsers;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestsSolvedByUsersMapper extends CommonMapper<TestsSolvedByUsersDto,TestsSolvedByUsers>{
}
