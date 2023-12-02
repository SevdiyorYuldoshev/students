package com.example.students_project.service.mapper;

import com.example.students_project.dto.PracticeDto;
import com.example.students_project.dto.SubjectDto;
import com.example.students_project.entity.Practice;
import com.example.students_project.entity.PracticeSolvedByUsers;
import com.example.students_project.entity.Subject;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-02T18:04:06+0500",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.7 (Amazon.com Inc.)"
)
@Component
public class SubjectMapperImpl implements SubjectMapper {

    @Override
    public SubjectDto toDto(Subject e) {
        if ( e == null ) {
            return null;
        }

        SubjectDto.SubjectDtoBuilder subjectDto = SubjectDto.builder();

        subjectDto.id( e.getId() );
        subjectDto.name( e.getName() );
        subjectDto.activity( e.getActivity() );
        subjectDto.users( e.getUsers() );
        subjectDto.practices( practiceListToPracticeDtoList( e.getPractices() ) );

        return subjectDto.build();
    }

    @Override
    public Subject toEntity(SubjectDto d) {
        if ( d == null ) {
            return null;
        }

        Subject subject = new Subject();

        subject.setId( d.getId() );
        subject.setName( d.getName() );
        subject.setActivity( d.getActivity() );
        subject.setUsers( d.getUsers() );
        subject.setPractices( practiceDtoListToPracticeList( d.getPractices() ) );

        return subject;
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
        practiceDto.setSubject( practice.getSubject() );
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
        practice.setSubject( practiceDto.getSubject() );
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
}
