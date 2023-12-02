package com.example.students_project.service.impl;

import com.example.students_project.dto.PracticeDto;
import com.example.students_project.dto.ResponseDto;
import com.example.students_project.dto.SubjectDto;
import com.example.students_project.dto.UsersDto;
import com.example.students_project.entity.Practice;
import com.example.students_project.entity.Subject;
import com.example.students_project.entity.Users;
import com.example.students_project.repository.PracticeRepository;
import com.example.students_project.rest.PracticeController;
import com.example.students_project.rest.UsersController;
import com.example.students_project.service.PracticeService;
import com.example.students_project.service.mapper.PracticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.students_project.service.validation.StatusCode.NOT_FOUND_CODE;
import static com.example.students_project.service.validation.StatusCode.OK_CODE;
import static com.example.students_project.service.validation.StatusMessage.NOT_FOUND;
import static com.example.students_project.service.validation.StatusMessage.OK;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
@RequiredArgsConstructor
public class PracticeServiceImpl implements PracticeService {
    private final PracticeMapper practiceMapper;
    private final PracticeRepository practiceRepository;
    @Override
    public ResponseDto<PracticeDto> addPractice(PracticeDto practiceDto) {
        practiceDto.setActivity(true);
        return ResponseDto.<PracticeDto>builder()
                .data(practiceMapper.toDto(practiceRepository.save(practiceMapper.toEntity(practiceDto))))
                .build();
    }

    @Override
    public ResponseDto<PracticeDto> updatePractice(PracticeDto practiceDto) {
        return null;
    }

    @Override
    public ResponseDto<PracticeDto> getPracticeById(Integer id) {
        Optional<Practice> byId = practiceRepository.findById(id);
        if(byId.isEmpty()){
            return ResponseDto.<PracticeDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .build();
        }
        Practice practice = byId.get();
        if(!practice.getActivity()){
            return ResponseDto.<PracticeDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .build();
        }
        return ResponseDto.<PracticeDto>builder()
                .data(practiceMapper.toDto(practice))
                .success(true)
                .code(OK_CODE)
                .message(OK)
                .build();
    }

    @Override
    public ResponseDto<Page<EntityModel<PracticeDto>>> getAllPractice(Integer page, Integer size) {
        Long count = practiceRepository.count();
        return ResponseDto.<Page<EntityModel<PracticeDto>>>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(practiceRepository
                        .findAll(PageRequest.of((count / size) <= page ? (count % size == 0 ? (int)(count / size)  - 1 : (int)(count / size)) : page, size))
                        .map(u -> {
                            EntityModel<PracticeDto> entityModel = EntityModel.of(practiceMapper.toDto(u));
                            try {
                                entityModel.add(linkTo(PracticeController.class
                                        .getDeclaredMethod("getPracticeById", Integer.class))
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
    public ResponseDto<PracticeDto> deletePracticeById(Integer id) {
        Optional<Practice> practiceOptional = practiceRepository.findById(id);
        if(practiceOptional.isEmpty()){
            return ResponseDto.<PracticeDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .build();
        }
        Practice practice = practiceOptional.get();
        if(!practice.getActivity()){
            return ResponseDto.<PracticeDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .build();
        }
        practice.setActivity(false);
        practiceRepository.save(practice);
        return ResponseDto.<PracticeDto>builder()
                .message("User with ID " + id + " is deleted")
                .code(OK_CODE)
                .data(practiceMapper.toDto(practice))
                .build();
    }

    @Override
    public ResponseDto<Page<EntityModel<PracticeDto>>> getBySubjectId(Integer subjectId, Integer page, Integer size) {
        Long count = practiceRepository.countAllBySubjectId(subjectId);
        if(count == 0){
            return ResponseDto.<Page<EntityModel<PracticeDto>>>builder()
                    .code(NOT_FOUND_CODE)
                    .message(NOT_FOUND)
                    .build();
        }
        return ResponseDto.<Page<EntityModel<PracticeDto>>>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(practiceRepository
                        .findAllBySubjectId(subjectId,PageRequest.of((count / size) <= page ? (count % size == 0 ? (int)(count / size)  - 1 : (int)(count / size)) : page, size))
                        .map(u -> {
                            EntityModel<PracticeDto> entityModel = EntityModel.of(practiceMapper.toDto(u));
                            try {
                                entityModel.add(linkTo(PracticeController.class
                                        .getDeclaredMethod("getPracticeById", Integer.class))
                                        .withSelfRel()
                                        .expand(u.getId()));
                            } catch (NoSuchMethodException e) {
                                throw new RuntimeException(e);
                            }
                            return entityModel;
                        }))
                .build();
    }
}
