package com.example.students_project.service.mapper;

import com.example.students_project.dto.TestsDto;
import com.example.students_project.entity.Tests;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestsMapper extends CommonMapper<TestsDto, Tests> {
}
