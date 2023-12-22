package com.example.students_project.entity;

import com.example.students_project.dto.TestInfoDto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Tests {
    @Id
    @GeneratedValue(generator = "tests_id_seq")
    @SequenceGenerator(name = "tests_id_seq", sequenceName = "tests_id_seq", allocationSize = 1)
    private Integer id;
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String trueAnswer;
    @ManyToOne
    @JoinColumn(name = "testsInfo_id")
    private TestsInfo testsInfo;
}
