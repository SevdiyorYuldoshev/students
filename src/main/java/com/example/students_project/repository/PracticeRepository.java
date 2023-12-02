package com.example.students_project.repository;

import com.example.students_project.entity.Practice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PracticeRepository extends JpaRepository<Practice, Integer> {
    Long countAllBySubjectId(Integer subject_id);
    Page<Practice> findAllBySubjectId(Integer subject_id, Pageable pageable);
}
