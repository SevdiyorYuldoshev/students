package com.example.students_project.service.impl;

import com.example.students_project.dto.ResponseDto;
import com.example.students_project.dto.TestsDto;
import com.example.students_project.entity.TestsInfo;
import com.example.students_project.repository.TestInfoRepository;
import com.example.students_project.repository.TestsRepository;
import com.example.students_project.service.TestsService;
import com.example.students_project.service.mapper.TestsInfoMapper;
import com.example.students_project.service.mapper.TestsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.students_project.service.validation.StatusCode.*;
import static com.example.students_project.service.validation.StatusMessage.NOT_FOUND;
import static com.example.students_project.service.validation.StatusMessage.OK;

@Service
@RequiredArgsConstructor
public class TestsServiceImpl implements TestsService {
    private final TestsMapper testsMapper;
    private final TestInfoRepository testInfoRepository;
    private final TestsRepository testsRepository;
    private final TestsInfoMapper testsInfoMapper;
    @Override
    public ResponseDto<List<TestsDto>> addTests(List<TestsDto> testsDtoList, Integer testInfoId) {
        Optional<TestsInfo> byId = testInfoRepository.findById(testInfoId);
        if(byId.isEmpty()){
            return ResponseDto.<List<TestsDto>>builder()
                    .code(NOT_FOUND_CODE)
                    .message(NOT_FOUND)
                    .build();
        }

        TestsInfo testsInfo = byId.get();
        if(testsInfo.getNumberOfTests() != testsDtoList.size()){
            return ResponseDto.<List<TestsDto>>builder()
                    .code(UNEXPECTED_ERROR_CODE)
                    .message("The number of tests did not match")
                    .build();
        }
        return ResponseDto.<List<TestsDto>>builder()
                .data(testsDtoList.stream().peek(t -> {
                    t.setTestInfoDto(testsInfoMapper.toDto(testsInfo));
                    testsRepository.save(testsMapper.toEntity(t));
                }).toList())
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .build();
    }

    @Override
    public ResponseDto<List<TestsDto>> getTestsByTestInfoId(Integer testInfoId) {
        Optional<TestsInfo> byId = testInfoRepository.findById(testInfoId);
        if(byId.isEmpty()){
            return ResponseDto.<List<TestsDto>>builder()
                    .code(NOT_FOUND_CODE)
                    .message(NOT_FOUND)
                    .build();
        }
        TestsInfo testsInfo = byId.get();
        return ResponseDto.<List<TestsDto>>builder()
                .data(testsRepository.findAllByTestsInfo(testsInfo).stream().map(testsMapper::toDto).toList())
                .success(true)
                .message(OK)
                .code(OK_CODE)
                .build();
    }

    @Override
    public ResponseDto<List<TestsDto>> deleteTestsByTestInfoId(Integer testInfoId) {
        Optional<TestsInfo> byId = testInfoRepository.findById(testInfoId);
        if(byId.isEmpty()){
            return ResponseDto.<List<TestsDto>>builder()
                    .code(NOT_FOUND_CODE)
                    .message(NOT_FOUND)
                    .build();
        }
        TestsInfo testsInfo = byId.get();

        testsRepository.deleteAllByTestsInfo(testsInfo);

        return ResponseDto.<List<TestsDto>>builder()
                .code(OK_CODE)
                .success(true)
                .message(OK)
                .build();
    }
}
