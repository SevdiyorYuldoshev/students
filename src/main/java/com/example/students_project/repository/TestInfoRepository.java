package com.example.students_project.repository;

import com.example.students_project.entity.Subject;
import com.example.students_project.entity.TestsInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestInfoRepository extends JpaRepository<TestsInfo, Integer> {
    Page<TestsInfo> findAllByActivityIsTrue(Pageable pageable);
    Long countAllByActivityIsTrue();
}
