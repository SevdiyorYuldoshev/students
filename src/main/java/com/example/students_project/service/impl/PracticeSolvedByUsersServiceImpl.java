package com.example.students_project.service.impl;

import com.example.students_project.dto.PracticeSolvedByUsersDto;
import com.example.students_project.dto.ResponseDto;
import com.example.students_project.dto.SubjectDto;
import com.example.students_project.entity.Practice;
import com.example.students_project.entity.PracticeSolvedByUsers;
import com.example.students_project.entity.Subject;
import com.example.students_project.entity.Users;
import com.example.students_project.repository.PracticeRepository;
import com.example.students_project.repository.PracticeSolvedByUsersRepository;
import com.example.students_project.repository.UserRepository;
import com.example.students_project.rest.PracticeSolvedByUsersController;
import com.example.students_project.service.PracticeSolvedByUsersService;
import com.example.students_project.service.mapper.PracticeMapper;
import com.example.students_project.service.mapper.PracticeSolvedByUsersMapper;
import com.example.students_project.service.mapper.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.students_project.service.validation.StatusCode.*;
import static com.example.students_project.service.validation.StatusMessage.*;
import static com.example.students_project.service.validation.StatusMessage.DATABASE_ERROR;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
@RequiredArgsConstructor
public class PracticeSolvedByUsersServiceImpl implements PracticeSolvedByUsersService {
    private final PracticeSolvedByUsersMapper practiceSolvedByUsersMapper;
    private final PracticeSolvedByUsersRepository practiceSolvedByUsersRepository;
    private final UserRepository userRepository;
    private final UsersMapper usersMapper;
    private final PracticeRepository practiceRepository;
    private final PracticeMapper practiceMapper;

    @Override
    public ResponseDto<PracticeSolvedByUsersDto> addPracticeSolvedByUsers(PracticeSolvedByUsersDto practiceSolvedByUsersDto, Integer userId, Integer practiceId) {
        Optional<Users> byId = userRepository.findById(userId);
        if(byId.isEmpty()){
            return ResponseDto.<PracticeSolvedByUsersDto>builder()
                    .message("User not found")
                    .code(NOT_FOUND_CODE)
                    .build();
        }

        Optional<Practice> byPracticeId = practiceRepository.findById(practiceId);
        if(byPracticeId.isEmpty()){
            return ResponseDto.<PracticeSolvedByUsersDto>builder()
                    .message("Practice not found")
                    .code(NOT_FOUND_CODE)
                    .build();
        }
        PracticeSolvedByUsers pSBu = practiceSolvedByUsersMapper.toEntity(practiceSolvedByUsersDto);

        pSBu.setPractice(practiceRepository.getReferenceById(practiceId));
        pSBu.setUsers(userRepository.getReferenceById(userId));
        practiceSolvedByUsersRepository.save(pSBu);
        return ResponseDto.<PracticeSolvedByUsersDto>builder()
                .data(practiceSolvedByUsersMapper.toDto(pSBu))
                .success(true)
                .message(OK)
                .code(OK_CODE)
                .build();
    }

    @Override
    public ResponseDto<PracticeSolvedByUsersDto> updatePracticeSolvedByUsers(PracticeSolvedByUsersDto practiceSolvedByUsersDto, Integer userId) {
        if (practiceSolvedByUsersDto.getId() == null){
            return ResponseDto.<PracticeSolvedByUsersDto>builder()
                    .message(NULL_VALUE)
                    .code(VALIDATION_ERROR_CODE)
                    .data(practiceSolvedByUsersDto)
                    .build();
        }

        Optional<PracticeSolvedByUsers> practiceSolvedByUsersOptional = practiceSolvedByUsersRepository.findById(practiceSolvedByUsersDto.getId());

        if (practiceSolvedByUsersOptional.isEmpty()){
            return ResponseDto.<PracticeSolvedByUsersDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .data(practiceSolvedByUsersDto)
                    .build();
        }
        PracticeSolvedByUsers pSBU = practiceSolvedByUsersOptional.get();


        if(pSBU.getUsers().getId() != userId) {
            return ResponseDto.<PracticeSolvedByUsersDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .build();
        }

        if (practiceSolvedByUsersDto.getGrade() != 0){
            pSBU.setGrade(practiceSolvedByUsersDto.getGrade());
        }
        if (practiceSolvedByUsersDto.getAnswersFileUrl() != null){
            pSBU.setAnswersFileUrl(practiceSolvedByUsersDto.getAnswersFileUrl());
        }

        practiceSolvedByUsersDto.setPercentage(practiceSolvedByUsersDto.getGrade()/practiceRepository.findMaxGradeById(practiceSolvedByUsersDto.getId()));
        pSBU.setPercentage(practiceSolvedByUsersDto.getPercentage());
        try {
            practiceSolvedByUsersRepository.save(pSBU);

            return ResponseDto.<PracticeSolvedByUsersDto>builder()
                    .data(practiceSolvedByUsersMapper.toDto(pSBU))
                    .success(true)
                    .message(OK)
                    .build();
        }catch (Exception e){
            return ResponseDto.<PracticeSolvedByUsersDto>builder()
                    .data(practiceSolvedByUsersMapper.toDto(pSBU))
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<PracticeSolvedByUsersDto> getPracticeSolvedByUsersById(Integer id) {
        Optional<PracticeSolvedByUsers> byId = practiceSolvedByUsersRepository.findById(id);
        PracticeSolvedByUsers practiceSolvedByUsers = byId.orElse(null);
        if(byId.isEmpty()){
            return ResponseDto.<PracticeSolvedByUsersDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .build();
        }
        return ResponseDto.<PracticeSolvedByUsersDto>builder()
                .data(practiceSolvedByUsersMapper.toDto(practiceSolvedByUsers))
                .success(true)
                .code(OK_CODE)
                .message(OK)
                .build();
    }

    @Override
    public ResponseDto<Page<EntityModel<PracticeSolvedByUsersDto>>> getAllPracticeSolvedByUsers(Integer page, Integer size) {
        Long count = practiceSolvedByUsersRepository.count();
        return ResponseDto.<Page<EntityModel<PracticeSolvedByUsersDto>>>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(practiceSolvedByUsersRepository
                        .findAll(PageRequest.of((count / size) <= page ? (count % size == 0 ? (int)(count / size)  - 1 : (int)(count / size)) : page, size))
                        .map(u -> {
                            EntityModel<PracticeSolvedByUsersDto> entityModel = EntityModel.of(practiceSolvedByUsersMapper.toDto(u));
                            try {
                                entityModel.add(linkTo(PracticeSolvedByUsersController.class
                                        .getDeclaredMethod("getAllPracticeSolvedByUsers", Integer.class))
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
    public ResponseDto<PracticeSolvedByUsersDto> deletePracticeSolvedByUsersByID(Integer id) {
        Optional<PracticeSolvedByUsers> byId = practiceSolvedByUsersRepository.findById(id);
        if(byId.isEmpty()){
            return ResponseDto.<PracticeSolvedByUsersDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .build();
        }
        PracticeSolvedByUsers practiceSolvedByUsers = byId.get();
        practiceSolvedByUsersRepository.delete(practiceSolvedByUsers);
        return ResponseDto.<PracticeSolvedByUsersDto>builder()
                .message("User with ID " + id + " is deleted")
                .code(OK_CODE)
                .data(practiceSolvedByUsersMapper.toDto(practiceSolvedByUsers))
                .build();
    }

    @Override
    public ResponseDto<Page<EntityModel<PracticeSolvedByUsersDto>>> getByPracticeId(Integer id, Integer page, Integer size) {
        Long count = practiceSolvedByUsersRepository.countAllByPracticeId(id);
        return ResponseDto.<Page<EntityModel<PracticeSolvedByUsersDto>>>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(practiceSolvedByUsersRepository
                        .findAll(PageRequest.of((count / size) <= page ? (count % size == 0 ? (int)(count / size)  - 1 : (int)(count / size)) : page, size))
                        .map(u -> {
                            EntityModel<PracticeSolvedByUsersDto> entityModel = EntityModel.of(practiceSolvedByUsersMapper.toDto(u));
                            try {
                                entityModel.add(linkTo(PracticeSolvedByUsersController.class
                                        .getDeclaredMethod("getAllPracticeSolvedByUsers", Integer.class))
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
    public ResponseDto<Page<EntityModel<PracticeSolvedByUsersDto>>> getByUserId(Integer id, Integer page, Integer size) {
        Long count = practiceSolvedByUsersRepository.countAllByUsersId(id);
        return ResponseDto.<Page<EntityModel<PracticeSolvedByUsersDto>>>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(practiceSolvedByUsersRepository
                        .findAll(PageRequest.of((count / size) <= page ? (count % size == 0 ? (int)(count / size)  - 1 : (int)(count / size)) : page, size))
                        .map(u -> {
                            EntityModel<PracticeSolvedByUsersDto> entityModel = EntityModel.of(practiceSolvedByUsersMapper.toDto(u));
                            try {
                                entityModel.add(linkTo(PracticeSolvedByUsersController.class
                                        .getDeclaredMethod("getAllPracticeSolvedByUsers", Integer.class))
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
    public ResponseDto<PracticeSolvedByUsersDto> makeGrade(Integer id, Float grade) {
        if(grade == null){
            return ResponseDto.<PracticeSolvedByUsersDto>builder()
                    .message(NULL_VALUE)
                    .code(NULL_VALUE_CODE)
                    .build();
        }
        Optional<PracticeSolvedByUsers> firstById = practiceSolvedByUsersRepository.findFirstById(id);
        if(firstById.isEmpty()){
            return ResponseDto.<PracticeSolvedByUsersDto>builder()
                    .code(NOT_FOUND_CODE)
                    .message(NOT_FOUND)
                    .build();
        }

        PracticeSolvedByUsers practiceSolvedByUsers = firstById.get();
        practiceSolvedByUsers.setGrade(grade);
        practiceSolvedByUsers.setPercentage(grade/practiceRepository.findMaxGradeById(practiceSolvedByUsers.getPractice().getId()));

        practiceSolvedByUsersRepository.save(practiceSolvedByUsers);
        return ResponseDto.<PracticeSolvedByUsersDto>builder()
                .message(OK)
                .code(OK_CODE)
                .success(true)
                .data(practiceSolvedByUsersMapper.toDto(practiceSolvedByUsers))
                .build();
    }
}
