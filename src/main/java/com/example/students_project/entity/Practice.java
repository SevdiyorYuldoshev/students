package com.example.students_project.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Practice {
    @Id
    @GeneratedValue(generator = "prac_id_seq")
    @SequenceGenerator(name = "prac_id_seq", sequenceName = "prac_id_seq", allocationSize = 1)
    private Integer id;
    private String practiceName;
    private LocalDateTime practiceDeadline;
    private String practiceFileUrl;
    private Boolean activity;
    private int maxGrade;
    @ManyToOne
    @JoinColumn(name = "subject_id")
//    @JsonBackReference
    private Subject subject;
    @OneToMany(mappedBy = "practice")
//    @JsonManagedReference
    private List<PracticeSolvedByUsers> practiceSolvedByUsersList;
}
