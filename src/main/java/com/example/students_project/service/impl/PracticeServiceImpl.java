package com.example.students_project.service.impl;

import com.example.students_project.dto.PracticeDto;
import com.example.students_project.dto.ResponseDto;
import com.example.students_project.dto.SubjectDto;
import com.example.students_project.dto.UsersDto;
import com.example.students_project.entity.Practice;
import com.example.students_project.entity.Subject;
import com.example.students_project.entity.Users;
import com.example.students_project.repository.PracticeRepository;
import com.example.students_project.repository.PracticeSolvedByUsersRepository;
import com.example.students_project.repository.SubjectRepository;
import com.example.students_project.rest.PracticeController;
import com.example.students_project.rest.UsersController;
import com.example.students_project.service.PracticeService;
import com.example.students_project.service.mapper.PracticeMapper;
import com.example.students_project.service.mapper.SubjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.students_project.service.validation.StatusCode.*;
import static com.example.students_project.service.validation.StatusMessage.*;
import static com.example.students_project.service.validation.StatusMessage.DATABASE_ERROR;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
@RequiredArgsConstructor
public class PracticeServiceImpl implements PracticeService {
    private final PracticeMapper practiceMapper;
    private final PracticeRepository practiceRepository;
    private final PracticeSolvedByUsersRepository practiceSolvedByUsersRepository;
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    @Override
    public ResponseDto<PracticeDto> addPractice(PracticeDto practiceDto, Integer subjectId) {
        Optional<Subject> byId = subjectRepository.findById(subjectId);
        if(byId.isEmpty()){
            return ResponseDto.<PracticeDto>builder()
                    .message("User not found")
                    .code(NOT_FOUND_CODE)
                    .build();
        }

        Practice practice =  practiceMapper.toEntity(practiceDto);
        practice.setActivity(true);
        practice.setSubject(subjectRepository.getReferenceById(subjectId));
        practiceRepository.save(practice);
        return ResponseDto.<PracticeDto>builder()
                .data(practiceMapper.toDto(practice))
                .build();
    }

    @Override
    public ResponseDto<PracticeDto> updatePractice(PracticeDto practiceDto, Integer userId) {
        if (practiceDto.getId() == null){
            return ResponseDto.<PracticeDto>builder()
                    .message(NULL_VALUE)
                    .code(VALIDATION_ERROR_CODE)
                    .data(practiceDto)
                    .build();
        }

        Optional<Practice> practiceOptional = practiceRepository.findById(practiceDto.getId());

        if (practiceOptional.isEmpty()){
            return ResponseDto.<PracticeDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .data(practiceDto)
                    .build();
        }
        Practice practice = practiceOptional.get();
        if(practice.getSubject().getUsers().getId() != userId) {
            return ResponseDto.<PracticeDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .build();
        }
        if(!practice.getActivity()){
            return ResponseDto.<PracticeDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .data(practiceDto)
                    .build();
        }
        if (practiceDto.getPracticeName() != null){
            practice.setPracticeName(practiceDto.getPracticeName());
        }
        if (practiceDto.getPracticeFileUrl() != null){
            practice.setPracticeFileUrl(practiceDto.getPracticeFileUrl());
        }
        if (practiceDto.getPracticeDeadline() != null &&
                LocalDateTime.now().isBefore(practiceDto.getPracticeDeadline()) &&
                !practiceSolvedByUsersRepository.existsByPracticeId(practice.getId())){
            practice.setPracticeDeadline(practiceDto.getPracticeDeadline());
        }
        if (practiceDto.getMaxGrade() != 0){
            practice.setMaxGrade(practiceDto.getMaxGrade());
        }

        try {
            practiceRepository.save(practice);

            return ResponseDto.<PracticeDto>builder()
                    .data(practiceMapper.toDto(practice))
                    .success(true)
                    .message(OK)
                    .code(OK_CODE)
                    .build();
        }catch (Exception e){
            return ResponseDto.<PracticeDto>builder()
                    .data(practiceMapper.toDto(practice))
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<PracticeDto> getPracticeById(Integer id) {
        Optional<Practice> byId = practiceRepository.findByIdAndActivityIsTrue(id);
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
        Long count = practiceRepository.countAllByActivityIsTrue();
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
