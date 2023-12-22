package com.example.students_project.service;

import com.example.students_project.dto.ResponseDto;
import com.example.students_project.dto.TestsSolvedByUsersDto;

import java.util.List;

public interface TestsSolvedByUsersService {
    ResponseDto<TestsSolvedByUsersDto> addTestsSolvedByUser(TestsSolvedByUsersDto testsSolvedByUsersDto, Integer userId, Integer testInfoId);

    ResponseDto<TestsSolvedByUsersDto> getTestsSolvedByUser(Integer userId);

    ResponseDto<List<TestsSolvedByUsersDto>> getAllTestsSolvedByUserByTestInfoId(Integer testInfoId);

    ResponseDto<TestsSolvedByUsersDto> updateTestsSolvedByUser(TestsSolvedByUsersDto testsSolvedByUsersDto);
}
