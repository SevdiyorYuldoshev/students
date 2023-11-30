package com.example.students_project.repository;

import com.example.students_project.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectController extends JpaRepository<Subject, Integer> {
}
