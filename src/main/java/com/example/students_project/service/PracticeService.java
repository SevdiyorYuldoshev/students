package com.example.students_project.service;

import com.example.students_project.dto.PracticeDto;
import com.example.students_project.dto.ResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;

public interface PracticeService {
    ResponseDto<PracticeDto> addPractice(PracticeDto practiceDto, Integer subjectId);

    ResponseDto<PracticeDto> updatePractice(PracticeDto practiceDto, Integer userId);

    ResponseDto<PracticeDto> getPracticeById(Integer id);

    ResponseDto<Page<EntityModel<PracticeDto>>> getAllPractice(Integer page, Integer size);

    ResponseDto<PracticeDto> deletePracticeById(Integer id);

    ResponseDto<Page<EntityModel<PracticeDto>>> getBySubjectId(Integer subjectId, Integer page, Integer size);
}
