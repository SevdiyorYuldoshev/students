package com.example.students_project.service;

import com.example.students_project.dto.PracticeSolvedByUsersDto;
import com.example.students_project.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

public interface PracticeSolvedByUsersService {
    ResponseDto<PracticeSolvedByUsersDto> addPracticeSolvedByUsers(PracticeSolvedByUsersDto practiceSolvedByUsersDto);

    ResponseDto<PracticeSolvedByUsersDto> updatePracticeSolvedByUsers(PracticeSolvedByUsersDto practiceSolvedByUsersDto);

    ResponseDto<PracticeSolvedByUsersDto> getPracticeSolvedByUsersById(Integer id);

    ResponseDto<Page<EntityModel<PracticeSolvedByUsersDto>>> getAllPracticeSolvedByUsers(Integer page, Integer size);

    ResponseDto<PracticeSolvedByUsersDto> deletePracticeSolvedByUsersByID(Integer id);
}
