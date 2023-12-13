package com.example.students_project.service;

import com.example.students_project.dto.PracticeSolvedByUsersDto;
import com.example.students_project.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

public interface PracticeSolvedByUsersService {
    ResponseDto<PracticeSolvedByUsersDto> addPracticeSolvedByUsers(PracticeSolvedByUsersDto practiceSolvedByUsersDto, Integer userId, Integer practiceId);

    ResponseDto<PracticeSolvedByUsersDto> updatePracticeSolvedByUsers(PracticeSolvedByUsersDto practiceSolvedByUsersDto, Integer userId);

    ResponseDto<PracticeSolvedByUsersDto> getPracticeSolvedByUsersById(Integer id);

    ResponseDto<Page<EntityModel<PracticeSolvedByUsersDto>>> getAllPracticeSolvedByUsers(Integer page, Integer size);

    ResponseDto<PracticeSolvedByUsersDto> deletePracticeSolvedByUsersByID(Integer id);

    ResponseDto<Page<EntityModel<PracticeSolvedByUsersDto>>> getByPracticeId(Integer id, Integer page, Integer size);

    ResponseDto<Page<EntityModel<PracticeSolvedByUsersDto>>> getByUserId(Integer id, Integer page, Integer size);

    ResponseDto<PracticeSolvedByUsersDto> makeGrade(Integer id, Float grade);
}
