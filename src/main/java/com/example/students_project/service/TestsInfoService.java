package com.example.students_project.service;

import com.example.students_project.dto.ResponseDto;
import com.example.students_project.dto.TestInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;

public interface TestsInfoService {
    ResponseDto<TestInfoDto> addTestsInfo(TestInfoDto testInfoDto, Integer subjectId);

    ResponseDto<TestInfoDto> updateTestsInfo(TestInfoDto testInfoDto);

    ResponseDto<TestInfoDto> getTestInfoById(Integer id);

    ResponseDto<Page<EntityModel<TestInfoDto>>> getAllTestsInfo(Integer page, Integer size);

    ResponseDto<TestInfoDto> deleteTestsInfo(Integer id);
}
