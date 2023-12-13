package com.example.students_project.service;

import com.example.students_project.dto.ResponseDto;
import com.example.students_project.dto.SubjectDto;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.multipart.MultipartFile;

public interface SubjectService {
    ResponseDto<SubjectDto> addSubject(SubjectDto subjectDto, Integer userId);

    ResponseDto<SubjectDto> updateSubject(SubjectDto subjectDto, Integer userId);

    ResponseDto<SubjectDto> getSubjectById(Integer id);

    ResponseDto<Page<EntityModel<SubjectDto>>> getAllSubject(Integer page, Integer size);

    ResponseDto<SubjectDto> deleteSubjectById(Integer id);

    ResponseDto<Page<EntityModel<SubjectDto>>> getSubjectByUserId(Integer id, Integer page, Integer size);
}
