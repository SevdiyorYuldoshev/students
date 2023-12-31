package com.example.students_project.service.impl;

import com.example.students_project.dto.ResponseDto;
import com.example.students_project.dto.SubjectDto;
import com.example.students_project.entity.Subject;
import com.example.students_project.entity.Users;
import com.example.students_project.repository.SubjectRepository;
import com.example.students_project.repository.UserRepository;
import com.example.students_project.rest.SubjectController;
import com.example.students_project.service.SubjectService;
import com.example.students_project.service.mapper.SubjectMapper;
import com.example.students_project.service.mapper.UsersMapper;
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
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final UserRepository userRepository;
    private final UsersMapper usersMapper;
    @Override
    public ResponseDto<SubjectDto> addSubject(SubjectDto subjectDto, Integer userId) {
        Optional<Users> byId = userRepository.findById(userId);
        if(byId.isEmpty()){
            return ResponseDto.<SubjectDto>builder()
                    .message("User not found")
                    .code(NOT_FOUND_CODE)
                    .build();
        }
        Subject subject = subjectMapper.toEntity(subjectDto);
        subject.setActivity(true);
        subject.setUsers(userRepository.getReferenceById(userId));
        subjectRepository.save(subject);
        return ResponseDto.<SubjectDto>builder()
                .success(true)
                .code(OK_CODE)
                .message(OK)
                .data(subjectMapper.toDto(subject))
                .build();
    }

    @Override
    public ResponseDto<SubjectDto> updateSubject(SubjectDto subjectDto, Integer userId) {
        if (subjectDto.getId() == null){
            return ResponseDto.<SubjectDto>builder()
                    .message(NULL_VALUE)
                    .code(VALIDATION_ERROR_CODE)
                    .data(subjectDto)
                    .build();
        }

        Optional<Subject> subjectOptional = subjectRepository.findById(subjectDto.getId());

        if (subjectOptional.isEmpty()){
            return ResponseDto.<SubjectDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .data(subjectDto)
                    .build();
        }
        Subject subject = subjectOptional.get();

        if(subject.getUsers().getId() != userId) {
            return ResponseDto.<SubjectDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .build();
        }
        if(!subject.getActivity()){
            return ResponseDto.<SubjectDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .data(subjectDto)
                    .build();
        }
        if (subjectDto.getName() != null){
            subject.setName(subjectDto.getName());
        }
        if (subjectDto.getLectureDocumentUrl() != null){
            subject.setLectureDocumentUrl(subjectDto.getLectureDocumentUrl());
        }

        try {
            subjectRepository.save(subject);

            return ResponseDto.<SubjectDto>builder()
                    .data(subjectMapper.toDto(subject))
                    .success(true)
                    .code(OK_CODE)
                    .message(OK)
                    .build();
        }catch (Exception e){
            return ResponseDto.<SubjectDto>builder()
                    .data(subjectMapper.toDto(subject))
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<SubjectDto> getSubjectById(Integer id) {
        Optional<Subject> byId = subjectRepository.findById(id);
        Subject subject = byId.orElse(null);
        if(byId.isEmpty() || !subject.getActivity()){
            return ResponseDto.<SubjectDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .build();
        }
        return ResponseDto.<SubjectDto>builder()
                .data(subjectMapper.toDto(subject))
                .success(true)
                .code(OK_CODE)
                .message(OK)
                .build();
    }

    @Override
    public ResponseDto<Page<EntityModel<SubjectDto>>> getAllSubject(Integer page, Integer size) {
        Long count = subjectRepository.countAllByActivityIsTrue();
        return ResponseDto.<Page<EntityModel<SubjectDto>>>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(subjectRepository
                        .findAllByActivityIsTrue(PageRequest.of((count / size) <= page ? (count % size == 0 ? (int)(count / size)  - 1 : (int)(count / size)) : page, size))
                        .map(u -> {
                            EntityModel<SubjectDto> entityModel = EntityModel.of(subjectMapper.toDto(u));
                            try {
                                entityModel.add(linkTo(SubjectController.class
                                        .getDeclaredMethod("getSubjectById", Integer.class))
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
    public ResponseDto<SubjectDto> deleteSubjectById(Integer id) {
        Optional<Subject> subjectOptional = subjectRepository.findById(id);

        Subject subject = subjectOptional.orElse(null);

        if(subjectOptional.isEmpty() || !subject.getActivity()){
            return ResponseDto.<SubjectDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .build();
        }
        subject.setActivity(false);
        subjectRepository.save(subject);
        return ResponseDto.<SubjectDto>builder()
                .message("Subject with ID " + id + " is deleted")
                .code(OK_CODE)
                .data(subjectMapper.toDto(subject))
                .build();
    }

    @Override
    public ResponseDto<Page<EntityModel<SubjectDto>>> getSubjectByUserId(Integer id, Integer page, Integer size) {
        Long count = subjectRepository.countAllByUsersId(id);
        if(count == 0) {
            return ResponseDto.<Page<EntityModel<SubjectDto>>>builder()
                    .code(NOT_FOUND_CODE)
                    .message(NOT_FOUND)
                    .build();
        }
        return ResponseDto.<Page<EntityModel<SubjectDto>>>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(subjectRepository
                        .findAllByUsersId(id,PageRequest.of((count / size) <= page ? (count % size == 0 ? (int)(count / size)  - 1 : (int)(count / size)) : page, size))
                        .map(s -> {
                            EntityModel<SubjectDto> entityModel = EntityModel.of(subjectMapper.toDto(s));
                            try {
                                entityModel.add(linkTo(SubjectController.class
                                        .getDeclaredMethod("getSubjectById", Integer.class))
                                        .withSelfRel()
                                        .expand(s.getId()));
                            } catch (NoSuchMethodException e) {
                                throw new RuntimeException(e);
                            }
                            return entityModel;
                        }))
                .build();
    }
}
