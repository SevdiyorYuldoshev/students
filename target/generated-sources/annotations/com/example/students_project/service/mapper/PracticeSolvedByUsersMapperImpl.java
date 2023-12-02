package com.example.students_project.service.mapper;

import com.example.students_project.dto.PracticeSolvedByUsersDto;
import com.example.students_project.entity.PracticeSolvedByUsers;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-02T18:04:05+0500",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.7 (Amazon.com Inc.)"
)
@Component
public class PracticeSolvedByUsersMapperImpl implements PracticeSolvedByUsersMapper {

    @Override
    public PracticeSolvedByUsersDto toDto(PracticeSolvedByUsers e) {
        if ( e == null ) {
            return null;
        }

        PracticeSolvedByUsersDto practiceSolvedByUsersDto = new PracticeSolvedByUsersDto();

        practiceSolvedByUsersDto.setId( e.getId() );
        practiceSolvedByUsersDto.setGrade( e.getGrade() );
        practiceSolvedByUsersDto.setPercentage( e.getPercentage() );
        practiceSolvedByUsersDto.setAnswersFileUrl( e.getAnswersFileUrl() );
        practiceSolvedByUsersDto.setUsers( e.getUsers() );
        practiceSolvedByUsersDto.setPractice( e.getPractice() );

        return practiceSolvedByUsersDto;
    }

    @Override
    public PracticeSolvedByUsers toEntity(PracticeSolvedByUsersDto d) {
        if ( d == null ) {
            return null;
        }

        PracticeSolvedByUsers practiceSolvedByUsers = new PracticeSolvedByUsers();

        practiceSolvedByUsers.setId( d.getId() );
        practiceSolvedByUsers.setGrade( d.getGrade() );
        practiceSolvedByUsers.setPercentage( d.getPercentage() );
        practiceSolvedByUsers.setAnswersFileUrl( d.getAnswersFileUrl() );
        practiceSolvedByUsers.setUsers( d.getUsers() );
        practiceSolvedByUsers.setPractice( d.getPractice() );

        return practiceSolvedByUsers;
    }
}
