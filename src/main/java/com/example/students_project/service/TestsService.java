package com.example.students_project.service;

import com.example.students_project.dto.ResponseDto;
import com.example.students_project.dto.TestsDto;

import java.util.List;

public interface TestsService {
    ResponseDto<List<TestsDto>> addTests(List<TestsDto> testsDtoList, Integer testInfoId);

    ResponseDto<List<TestsDto>> getTestsByTestInfoId(Integer testInfoId);

    ResponseDto<List<TestsDto>> deleteTestsByTestInfoId(Integer testInfoId);
}
