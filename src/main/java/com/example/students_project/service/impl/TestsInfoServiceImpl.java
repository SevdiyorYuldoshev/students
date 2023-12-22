package com.example.students_project.service.impl;

import com.example.students_project.dto.ResponseDto;
import com.example.students_project.dto.TestInfoDto;
import com.example.students_project.entity.Subject;
import com.example.students_project.entity.TestsInfo;
import com.example.students_project.repository.SubjectRepository;
import com.example.students_project.repository.TestInfoRepository;
import com.example.students_project.repository.UserRepository;
import com.example.students_project.rest.TestInfoController;
import com.example.students_project.service.TestsInfoService;
import com.example.students_project.service.mapper.TestsInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.students_project.service.validation.StatusCode.*;
import static com.example.students_project.service.validation.StatusMessage.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
@RequiredArgsConstructor
public class TestsInfoServiceImpl implements TestsInfoService {
    private final TestsInfoMapper testsInfoMapper;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final TestInfoRepository testInfoRepository;
    @Override
    public ResponseDto<TestInfoDto> addTestsInfo(TestInfoDto testInfoDto, Integer subjectId) {
        Optional<Subject> byId = subjectRepository.findById(subjectId);
        if(byId.isEmpty()){
            return ResponseDto.<TestInfoDto>builder()
                    .message("User not found")
                    .code(NOT_FOUND_CODE)
                    .build();
        }
        TestsInfo testsInfo = testsInfoMapper.toEntity(testInfoDto);
        testsInfo.setActivity(true);
        testsInfo.setSubject(subjectRepository.getReferenceById(subjectId));
        testInfoRepository.save(testsInfo);
        return ResponseDto.<TestInfoDto>builder()
                .success(true)
                .code(OK_CODE)
                .message(OK)
                .data(testsInfoMapper.toDto(testsInfo))
                .build();
    }

    @Override
    public ResponseDto<TestInfoDto> updateTestsInfo(TestInfoDto testsInfoDto) {
        if (testsInfoDto.getId() == null){
            return ResponseDto.<TestInfoDto>builder()
                    .message(NULL_VALUE)
                    .code(VALIDATION_ERROR_CODE)
                    .data(testsInfoDto)
                    .build();
        }

        Optional<TestsInfo> testsInfoOptional = testInfoRepository.findById(testsInfoDto.getId());

        if (testsInfoOptional.isEmpty()){
            return ResponseDto.<TestInfoDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .data(testsInfoDto)
                    .build();
        }
        TestsInfo testsInfo = testsInfoOptional.get();

        if(!testsInfo.isActivity()){
            return ResponseDto.<TestInfoDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .data(testsInfoDto)
                    .build();
        }
        if (testsInfoDto.getTestName() != null){
            testsInfo.setTestName(testsInfoDto.getTestName());
        }
        if (testsInfoDto.getTestDeadline() != null){
            testsInfo.setTestDeadline(testsInfoDto.getTestDeadline());
        }

        try {
            testInfoRepository.save(testsInfo);

            return ResponseDto.<TestInfoDto>builder()
                    .data(testsInfoMapper.toDto(testsInfo))
                    .success(true)
                    .code(OK_CODE)
                    .message(OK)
                    .build();
        }catch (Exception e){
            return ResponseDto.<TestInfoDto>builder()
                    .data(testsInfoMapper.toDto(testsInfo))
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<TestInfoDto> getTestInfoById(Integer id) {
        Optional<TestsInfo> byId = testInfoRepository.findById(id);
        TestsInfo testsInfo = byId.orElse(null);
        if(byId.isEmpty() || !testsInfo.isActivity()){
            return ResponseDto.<TestInfoDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .build();
        }
        return ResponseDto.<TestInfoDto>builder()
                .data(testsInfoMapper.toDto(testsInfo))
                .success(true)
                .code(OK_CODE)
                .message(OK)
                .build();
    }

    @Override
    public ResponseDto<Page<EntityModel<TestInfoDto>>> getAllTestsInfo(Integer page, Integer size) {
        Long count = testInfoRepository.countAllByActivityIsTrue();
        return ResponseDto.<Page<EntityModel<TestInfoDto>>>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(testInfoRepository
                        .findAllByActivityIsTrue(PageRequest.of((count / size) <= page ? (count % size == 0 ? (int)(count / size)  - 1 : (int)(count / size)) : page, size))
                        .map(u -> {
                            EntityModel<TestInfoDto> entityModel = EntityModel.of(testsInfoMapper.toDto(u));
                            try {
                                entityModel.add(linkTo(TestInfoController.class
                                        .getDeclaredMethod("getTestsInfoById", Integer.class))
                                        .withSelfRel()
                                        .expand(u.getId()));
                            } catch (NoSuchMethodException e) {
                                throw new RuntimeException(e);
                            }
                            return entityModel;
                        }))
                .build();
    }

    @Override
    public ResponseDto<TestInfoDto> deleteTestsInfo(Integer id) {
        Optional<TestsInfo> testsInfoOptional = testInfoRepository.findById(id);

        TestsInfo testsInfo = testsInfoOptional.orElse(null);

        if(testsInfoOptional.isEmpty() || !testsInfo.isActivity()){
            return ResponseDto.<TestInfoDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .build();
        }
        testsInfo.setActivity(false);
        testInfoRepository.save(testsInfo);
        return ResponseDto.<TestInfoDto>builder()
                .message("TestsInfo with ID " + id + " is deleted")
                .code(OK_CODE)
                .data(testsInfoMapper.toDto(testsInfo))
                .build();
    }
}
