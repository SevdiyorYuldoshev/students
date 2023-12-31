package com.example.students_project.service.mapper;

import com.example.students_project.dto.PracticeDto;
import com.example.students_project.dto.PracticeSolvedByUsersDto;
import com.example.students_project.dto.SubjectDto;
import com.example.students_project.dto.UsersDto;
import com.example.students_project.entity.Practice;
import com.example.students_project.entity.PracticeSolvedByUsers;
import com.example.students_project.entity.Subject;
import com.example.students_project.entity.Users;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-13T15:09:39+0500",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.7 (Amazon.com Inc.)"
)
@Component
public class UsersMapperImpl implements UsersMapper {

    @Override
    public UsersDto toDto(Users e) {
        if ( e == null ) {
            return null;
        }

        UsersDto.UsersDtoBuilder usersDto = UsersDto.builder();

        usersDto.id( e.getId() );
        usersDto.firstname( e.getFirstname() );
        usersDto.lastname( e.getLastname() );
        usersDto.username( e.getUsername() );
        usersDto.password( e.getPassword() );
        usersDto.email( e.getEmail() );
        usersDto.phoneNumber( e.getPhoneNumber() );
        usersDto.role( e.getRole() );
        usersDto.createdAt( e.getCreatedAt() );
        usersDto.description( e.getDescription() );
        usersDto.activity( e.getActivity() );
        usersDto.subjects( subjectListToSubjectDtoList( e.getSubjects() ) );
        usersDto.practiceSolvedByUsersList( practiceSolvedByUsersListToPracticeSolvedByUsersDtoList( e.getPracticeSolvedByUsersList() ) );

        return usersDto.build();
    }

    @Override
    public Users toEntity(UsersDto d) {
        if ( d == null ) {
            return null;
        }

        Users.UsersBuilder users = Users.builder();

        users.id( d.getId() );
        users.firstname( d.getFirstname() );
        users.lastname( d.getLastname() );
        users.username( d.getUsername() );
        users.password( d.getPassword() );
        users.role( d.getRole() );
        users.email( d.getEmail() );
        users.phoneNumber( d.getPhoneNumber() );
        users.description( d.getDescription() );
        users.createdAt( d.getCreatedAt() );
        users.activity( d.getActivity() );
        users.subjects( subjectDtoListToSubjectList( d.getSubjects() ) );
        users.practiceSolvedByUsersList( practiceSolvedByUsersDtoListToPracticeSolvedByUsersList( d.getPracticeSolvedByUsersList() ) );

        return users.build();
    }

    protected PracticeDto practiceToPracticeDto(Practice practice) {
        if ( practice == null ) {
            return null;
        }

        PracticeDto practiceDto = new PracticeDto();

        practiceDto.setId( practice.getId() );
        practiceDto.setPracticeName( practice.getPracticeName() );
        practiceDto.setPracticeDeadline( practice.getPracticeDeadline() );
        practiceDto.setPracticeFileUrl( practice.getPracticeFileUrl() );
        practiceDto.setActivity( practice.getActivity() );
        practiceDto.setMaxGrade( practice.getMaxGrade() );
        List<PracticeSolvedByUsers> list = practice.getPracticeSolvedByUsersList();
        if ( list != null ) {
            practiceDto.setPracticeSolvedByUsersList( new ArrayList<PracticeSolvedByUsers>( list ) );
        }

        return practiceDto;
    }

    protected List<PracticeDto> practiceListToPracticeDtoList(List<Practice> list) {
        if ( list == null ) {
            return null;
        }

        List<PracticeDto> list1 = new ArrayList<PracticeDto>( list.size() );
        for ( Practice practice : list ) {
            list1.add( practiceToPracticeDto( practice ) );
        }

        return list1;
    }

    protected SubjectDto subjectToSubjectDto(Subject subject) {
        if ( subject == null ) {
            return null;
        }

        SubjectDto.SubjectDtoBuilder subjectDto = SubjectDto.builder();

        subjectDto.id( subject.getId() );
        subjectDto.name( subject.getName() );
        subjectDto.lectureDocumentUrl( subject.getLectureDocumentUrl() );
        subjectDto.activity( subject.getActivity() );
        subjectDto.practices( practiceListToPracticeDtoList( subject.getPractices() ) );

        return subjectDto.build();
    }

    protected List<SubjectDto> subjectListToSubjectDtoList(List<Subject> list) {
        if ( list == null ) {
            return null;
        }

        List<SubjectDto> list1 = new ArrayList<SubjectDto>( list.size() );
        for ( Subject subject : list ) {
            list1.add( subjectToSubjectDto( subject ) );
        }

        return list1;
    }

    protected PracticeSolvedByUsersDto practiceSolvedByUsersToPracticeSolvedByUsersDto(PracticeSolvedByUsers practiceSolvedByUsers) {
        if ( practiceSolvedByUsers == null ) {
            return null;
        }

        PracticeSolvedByUsersDto practiceSolvedByUsersDto = new PracticeSolvedByUsersDto();

        practiceSolvedByUsersDto.setId( practiceSolvedByUsers.getId() );
        practiceSolvedByUsersDto.setGrade( practiceSolvedByUsers.getGrade() );
        practiceSolvedByUsersDto.setPercentage( practiceSolvedByUsers.getPercentage() );
        practiceSolvedByUsersDto.setAnswersFileUrl( practiceSolvedByUsers.getAnswersFileUrl() );

        return practiceSolvedByUsersDto;
    }

    protected List<PracticeSolvedByUsersDto> practiceSolvedByUsersListToPracticeSolvedByUsersDtoList(List<PracticeSolvedByUsers> list) {
        if ( list == null ) {
            return null;
        }

        List<PracticeSolvedByUsersDto> list1 = new ArrayList<PracticeSolvedByUsersDto>( list.size() );
        for ( PracticeSolvedByUsers practiceSolvedByUsers : list ) {
            list1.add( practiceSolvedByUsersToPracticeSolvedByUsersDto( practiceSolvedByUsers ) );
        }

        return list1;
    }

    protected Practice practiceDtoToPractice(PracticeDto practiceDto) {
        if ( practiceDto == null ) {
            return null;
        }

        Practice practice = new Practice();

        practice.setId( practiceDto.getId() );
        practice.setPracticeName( practiceDto.getPracticeName() );
        practice.setPracticeDeadline( practiceDto.getPracticeDeadline() );
        practice.setPracticeFileUrl( practiceDto.getPracticeFileUrl() );
        practice.setActivity( practiceDto.getActivity() );
        practice.setMaxGrade( practiceDto.getMaxGrade() );
        List<PracticeSolvedByUsers> list = practiceDto.getPracticeSolvedByUsersList();
        if ( list != null ) {
            practice.setPracticeSolvedByUsersList( new ArrayList<PracticeSolvedByUsers>( list ) );
        }

        return practice;
    }

    protected List<Practice> practiceDtoListToPracticeList(List<PracticeDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Practice> list1 = new ArrayList<Practice>( list.size() );
        for ( PracticeDto practiceDto : list ) {
            list1.add( practiceDtoToPractice( practiceDto ) );
        }

        return list1;
    }

    protected Subject subjectDtoToSubject(SubjectDto subjectDto) {
        if ( subjectDto == null ) {
            return null;
        }

        Subject subject = new Subject();

        subject.setId( subjectDto.getId() );
        subject.setName( subjectDto.getName() );
        subject.setLectureDocumentUrl( subjectDto.getLectureDocumentUrl() );
        subject.setActivity( subjectDto.getActivity() );
        subject.setPractices( practiceDtoListToPracticeList( subjectDto.getPractices() ) );

        return subject;
    }

    protected List<Subject> subjectDtoListToSubjectList(List<SubjectDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Subject> list1 = new ArrayList<Subject>( list.size() );
        for ( SubjectDto subjectDto : list ) {
            list1.add( subjectDtoToSubject( subjectDto ) );
        }

        return list1;
    }

    protected PracticeSolvedByUsers practiceSolvedByUsersDtoToPracticeSolvedByUsers(PracticeSolvedByUsersDto practiceSolvedByUsersDto) {
        if ( practiceSolvedByUsersDto == null ) {
            return null;
        }

        PracticeSolvedByUsers practiceSolvedByUsers = new PracticeSolvedByUsers();

        practiceSolvedByUsers.setId( practiceSolvedByUsersDto.getId() );
        practiceSolvedByUsers.setGrade( practiceSolvedByUsersDto.getGrade() );
        practiceSolvedByUsers.setPercentage( practiceSolvedByUsersDto.getPercentage() );
        practiceSolvedByUsers.setAnswersFileUrl( practiceSolvedByUsersDto.getAnswersFileUrl() );

        return practiceSolvedByUsers;
    }

    protected List<PracticeSolvedByUsers> practiceSolvedByUsersDtoListToPracticeSolvedByUsersList(List<PracticeSolvedByUsersDto> list) {
        if ( list == null ) {
            return null;
        }

        List<PracticeSolvedByUsers> list1 = new ArrayList<PracticeSolvedByUsers>( list.size() );
        for ( PracticeSolvedByUsersDto practiceSolvedByUsersDto : list ) {
            list1.add( practiceSolvedByUsersDtoToPracticeSolvedByUsers( practiceSolvedByUsersDto ) );
        }

        return list1;
    }
}
