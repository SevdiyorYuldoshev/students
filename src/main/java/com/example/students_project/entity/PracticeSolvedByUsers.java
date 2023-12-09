package com.example.students_project.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PracticeSolvedByUsers {
    @Id
    @GeneratedValue(generator = "prac_sol_by_us_id_seq")
    @SequenceGenerator(name = "prac_sol_by_us_id_seq", sequenceName = "prac_sol_by_us_id_seq", allocationSize = 1)
    private Integer id;
    private float grade;
    private float percentage;
    private String answersFileUrl;
    @ManyToOne
    @JoinColumn(name = "user_id")
//    @JsonBackReference
    private Users users;
    @ManyToOne
    @JoinColumn(name = "practice_id")
//    @JsonBackReference
    private Practice practice;

}
