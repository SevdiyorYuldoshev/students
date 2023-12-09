package com.example.students_project.repository;

import com.example.students_project.entity.Practice;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PracticeRepository extends JpaRepository<Practice, Integer> {
    Long countAllBySubjectId(Integer subject_id);
    Page<Practice> findAllBySubjectId(Integer subject_id, Pageable pageable);

    @Query(value = "select e.maxGrade from Practice e where e.id = :id", nativeQuery = true)
    Float findMaxGradeById(@Param("id")Integer id);

    Optional<Practice> findByIdAndActivityIsTrue(Integer id);
    Long countAllByActivityIsTrue();
}
