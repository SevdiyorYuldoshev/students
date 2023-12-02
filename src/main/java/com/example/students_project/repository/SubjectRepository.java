package com.example.students_project.repository;

import com.example.students_project.entity.Practice;
import com.example.students_project.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    Page<Subject> findAllByActivityIsTrue(Pageable pageable);
    Long countAllByActivityIsTrue();

    Long countAllByUsersId(Integer subject_id);
    Page<Subject> findAllByUsersId(Integer subject_id, Pageable pageable);
}
