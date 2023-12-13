package com.example.students_project.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Users {
    @Id
    @GeneratedValue(generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    private Integer id;
    @Column(length = 30)
    private String firstname;
    @Column(length = 30)
    private String lastname;
    @Column(unique = true, length = 30, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private String role;
    private String email;
    private String phoneNumber;
    private String description;
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private int activity;
    @OneToMany(mappedBy = "users")
    private List<Subject> subjects;
    @OneToMany(mappedBy = "users")
    private List<PracticeSolvedByUsers> practiceSolvedByUsersList;
    @OneToOne(mappedBy = "users")
    @JoinColumn(name = "image_id")
    private Image image;

}
