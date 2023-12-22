package com.example.students_project.entity;


import com.example.students_project.dto.TestInfoDto;
import com.example.students_project.dto.UsersDto;
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
public class TestsSolvedByUsers {
    @Id
    @GeneratedValue(generator = "TSBU_id_seq")
    @SequenceGenerator(name = "TSBU_id_seq", sequenceName = "TSBU_id_seq", allocationSize = 1)
    private Integer id;
    private int numberOfCorrect;
    private float percentage;
    @ManyToOne
    @JoinColumn(name = "tests_id")
    private TestsInfo testsInfo;
    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;
}
