package com.example.students_project.service.mapper;

import com.example.students_project.dto.SubjectDto;
import com.example.students_project.entity.Subject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectMapper extends CommonMapper<SubjectDto, Subject> {
}
