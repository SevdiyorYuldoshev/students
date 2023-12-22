package com.example.students_project.service.impl;

import com.example.students_project.dto.ResponseDto;
import com.example.students_project.dto.TestsSolvedByUsersDto;
import com.example.students_project.entity.TestsInfo;
import com.example.students_project.entity.TestsSolvedByUsers;
import com.example.students_project.entity.Users;
import com.example.students_project.repository.TestInfoRepository;
import com.example.students_project.repository.TestsSolvedByUsersRepository;
import com.example.students_project.repository.UserRepository;
import com.example.students_project.service.TestsSolvedByUsersService;
import com.example.students_project.service.mapper.TestsSolvedByUsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.students_project.service.validation.StatusCode.*;
import static com.example.students_project.service.validation.StatusMessage.*;

@Service
@RequiredArgsConstructor
public class TestsSolvedByUsersServiceImpl implements TestsSolvedByUsersService {
    private final TestsSolvedByUsersMapper testsSolvedByUsersMapper;
    private final TestsSolvedByUsersRepository testsSolvedByUsersRepository;
    private final UserRepository userRepository;
    private final TestInfoRepository testInfoRepository;
    @Override
    public ResponseDto<TestsSolvedByUsersDto> addTestsSolvedByUser(TestsSolvedByUsersDto testsSolvedByUsersDto, Integer userId, Integer testInfoId) {
        Optional<Users> byId = userRepository.findById(userId);
        if(byId.isEmpty()){
            return ResponseDto.<TestsSolvedByUsersDto>builder()
                    .code(NOT_FOUND_CODE)
                    .message(NOT_FOUND)
                    .build();
        }
        Users users = byId.get();
        Optional<TestsInfo> byId1 = testInfoRepository.findById(testInfoId);
        if(byId1.isEmpty()){
            return ResponseDto.<TestsSolvedByUsersDto>builder()
                    .code(NOT_FOUND_CODE)
                    .message(NOT_FOUND)
                    .build();
        }
        TestsSolvedByUsers entity = testsSolvedByUsersMapper.toEntity(testsSolvedByUsersDto);
        entity.setTestsInfo(byId1.get());
        entity.setUsers(users);
        entity.setPercentage((float) entity.getNumberOfCorrect() /entity.getTestsInfo().getNumberOfTests());
        return ResponseDto.<TestsSolvedByUsersDto>builder()
                .data(testsSolvedByUsersMapper.toDto(entity))
                .message(OK)
                .code(OK_CODE)
                .success(true)
                .build();
    }

    @Override
    public ResponseDto<TestsSolvedByUsersDto> getTestsSolvedByUser(Integer userId) {
        Optional<Users> byId = userRepository.findById(userId);
        if(byId.isEmpty()){
            return ResponseDto.<TestsSolvedByUsersDto>builder()
                    .code(NOT_FOUND_CODE)
                    .message(NOT_FOUND)
                    .build();
        }
        Users users = byId.get();
        Optional<TestsSolvedByUsers> byUsers = testsSolvedByUsersRepository.findFirstByUsers(users);
        if(byUsers.isEmpty()){
            return ResponseDto.<TestsSolvedByUsersDto>builder()
                    .code(NOT_FOUND_CODE)
                    .message(NOT_FOUND)
                    .build();
        }
        TestsSolvedByUsers testsSolvedByUsers = byUsers.get();
        return ResponseDto.<TestsSolvedByUsersDto>builder()
                .success(true)
                .code(OK_CODE)
                .message(OK)
                .data(testsSolvedByUsersMapper.toDto(testsSolvedByUsers))
                .build();
    }

    @Override
    public ResponseDto<List<TestsSolvedByUsersDto>> getAllTestsSolvedByUserByTestInfoId(Integer testInfoId) {
        Optional<TestsInfo> byId = testInfoRepository.findById(testInfoId);
        if(byId.isEmpty()){
            return ResponseDto.<List<TestsSolvedByUsersDto>>builder()
                    .code(NOT_FOUND_CODE)
                    .message(NOT_FOUND)
                    .build();
        }
        List<TestsSolvedByUsersDto> allByTestsInfo = testsSolvedByUsersRepository.findAllByTestsInfo(byId.get())
                .stream().map(testsSolvedByUsersMapper::toDto).toList();
        return ResponseDto.<List<TestsSolvedByUsersDto>>builder()
                .success(true)
                .data(allByTestsInfo)
                .message(OK)
                .code(OK_CODE)
                .build();
    }

    @Override
    public ResponseDto<TestsSolvedByUsersDto> updateTestsSolvedByUser(TestsSolvedByUsersDto testsSolvedByUsersDto) {
        if (testsSolvedByUsersDto.getId() == null){
            return ResponseDto.<TestsSolvedByUsersDto>builder()
                    .message(NULL_VALUE)
                    .code(VALIDATION_ERROR_CODE)
                    .data(testsSolvedByUsersDto)
                    .build();
        }

        Optional<TestsSolvedByUsers> testsInfoOptional = testsSolvedByUsersRepository.findById(testsSolvedByUsersDto.getId());

        if (testsInfoOptional.isEmpty()){
            return ResponseDto.<TestsSolvedByUsersDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .data(testsSolvedByUsersDto)
                    .build();
        }
        TestsSolvedByUsers testsSolvedByUsers = testsInfoOptional.get();


        if (testsSolvedByUsersDto.getNumberOfCorrect() != 0){
            testsSolvedByUsers.setNumberOfCorrect(testsSolvedByUsersDto.getNumberOfCorrect());
        }
        if (testsSolvedByUsersDto.getPercentage() != 0){
            testsSolvedByUsers.setPercentage(testsSolvedByUsersDto.getPercentage());
        }

        try {
            testsSolvedByUsersRepository.save(testsSolvedByUsers);

            return ResponseDto.<TestsSolvedByUsersDto>builder()
                    .data(testsSolvedByUsersMapper.toDto(testsSolvedByUsers))
                    .success(true)
                    .code(OK_CODE)
                    .message(OK)
                    .build();
        }catch (Exception e){
            return ResponseDto.<TestsSolvedByUsersDto>builder()
                    .data(testsSolvedByUsersMapper.toDto(testsSolvedByUsers))
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }
}
