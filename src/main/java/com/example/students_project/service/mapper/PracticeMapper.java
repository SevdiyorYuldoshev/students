package com.example.students_project.service.mapper;

import com.example.students_project.dto.PracticeDto;
import com.example.students_project.entity.Practice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PracticeMapper extends CommonMapper<PracticeDto, Practice> {
}
