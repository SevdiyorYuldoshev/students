package com.example.students_project.service.impl;

import com.example.students_project.dto.PracticeDto;
import com.example.students_project.dto.PracticeSolvedByUsersDto;
import com.example.students_project.dto.ResponseDto;
import com.example.students_project.entity.Practice;
import com.example.students_project.entity.PracticeSolvedByUsers;
import com.example.students_project.repository.PracticeSolvedByUsersRepository;
import com.example.students_project.rest.PracticeSolvedByUsersController;
import com.example.students_project.rest.UsersController;
import com.example.students_project.service.PracticeSolvedByUsersService;
import com.example.students_project.service.mapper.PracticeSolvedByUsersMapper;
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
public class PracticeSolvedByUsersServiceImpl implements PracticeSolvedByUsersService {
    private final PracticeSolvedByUsersMapper practiceSolvedByUsersMapper;
    private final PracticeSolvedByUsersRepository practiceSolvedByUsersRepository;

    @Override
    public ResponseDto<PracticeSolvedByUsersDto> addPracticeSolvedByUsers(PracticeSolvedByUsersDto practiceSolvedByUsersDto) {
        return ResponseDto.<PracticeSolvedByUsersDto>builder()
                .data(practiceSolvedByUsersMapper.toDto(practiceSolvedByUsersRepository.save(practiceSolvedByUsersMapper.toEntity(practiceSolvedByUsersDto))))
                .build();
    }

    @Override
    public ResponseDto<PracticeSolvedByUsersDto> updatePracticeSolvedByUsers(PracticeSolvedByUsersDto practiceSolvedByUsersDto) {
        return null;
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
}
