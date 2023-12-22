package com.example.students_project.service.mapper;

import com.example.students_project.dto.TestInfoDto;
import com.example.students_project.entity.TestsInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestsInfoMapper extends CommonMapper<TestInfoDto, TestsInfo>{
}
