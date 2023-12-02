package com.example.students_project.service.mapper;

import com.example.students_project.dto.PracticeSolvedByUsersDto;
import com.example.students_project.entity.PracticeSolvedByUsers;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PracticeSolvedByUsersMapper extends CommonMapper<PracticeSolvedByUsersDto, PracticeSolvedByUsers>{
}
