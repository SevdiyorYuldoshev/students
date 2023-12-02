package com.example.students_project.service.mapper;

import com.example.students_project.dto.PracticeDto;
import com.example.students_project.entity.Practice;
import com.example.students_project.entity.PracticeSolvedByUsers;
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
public class PracticeMapperImpl implements PracticeMapper {

    @Override
    public PracticeDto toDto(Practice e) {
        if ( e == null ) {
            return null;
        }

        PracticeDto practiceDto = new PracticeDto();

        practiceDto.setId( e.getId() );
        practiceDto.setPracticeName( e.getPracticeName() );
        practiceDto.setPracticeDeadline( e.getPracticeDeadline() );
        practiceDto.setPracticeFileUrl( e.getPracticeFileUrl() );
        practiceDto.setActivity( e.getActivity() );
        practiceDto.setMaxGrade( e.getMaxGrade() );
        practiceDto.setSubject( e.getSubject() );
        List<PracticeSolvedByUsers> list = e.getPracticeSolvedByUsersList();
        if ( list != null ) {
            practiceDto.setPracticeSolvedByUsersList( new ArrayList<PracticeSolvedByUsers>( list ) );
        }

        return practiceDto;
    }

    @Override
    public Practice toEntity(PracticeDto d) {
        if ( d == null ) {
            return null;
        }

        Practice practice = new Practice();

        practice.setId( d.getId() );
        practice.setPracticeName( d.getPracticeName() );
        practice.setPracticeDeadline( d.getPracticeDeadline() );
        practice.setPracticeFileUrl( d.getPracticeFileUrl() );
        practice.setActivity( d.getActivity() );
        practice.setMaxGrade( d.getMaxGrade() );
        practice.setSubject( d.getSubject() );
        List<PracticeSolvedByUsers> list = d.getPracticeSolvedByUsersList();
        if ( list != null ) {
            practice.setPracticeSolvedByUsersList( new ArrayList<PracticeSolvedByUsers>( list ) );
        }

        return practice;
    }
}
