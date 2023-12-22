package com.example.students_project.entity;

import com.example.students_project.dto.SubjectDto;
import com.example.students_project.dto.TestsDto;
import com.example.students_project.dto.TestsSolvedByUsersDto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TestsInfo {
    @Id
    @GeneratedValue(generator = "tests_id_seq")
    @SequenceGenerator(name = "tests_id_seq", sequenceName = "tests_id_seq", allocationSize = 1)
    private Integer id;
    private String testName;
    private Integer numberOfTests;
    private LocalDateTime testDeadline;
    private boolean activity;
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
    @OneToMany(mappedBy = "testsInfo")
    private List<TestsSolvedByUsers> testsSolvedByUsers;
    @OneToMany(mappedBy = "testsInfo")
    private List<Tests> tests;
}
