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

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Subject {
    @Id
    @GeneratedValue(generator = "sub_id_seq")
    @SequenceGenerator(name = "sub_id_seq", sequenceName = "sub_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    @Column(unique = true)
    private String lecture_document_url;
    private Boolean activity;
    @ManyToOne
    @JoinColumn(name = "users_id")
//    @JsonBackReference
    private Users users;
    @OneToMany(mappedBy = "subject")
//    @JsonManagedReference
    private List<Practice> practices;
}
