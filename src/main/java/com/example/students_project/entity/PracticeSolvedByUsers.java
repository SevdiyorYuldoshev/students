package com.example.students_project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
    private Float grade;
    private Float percentage;
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
