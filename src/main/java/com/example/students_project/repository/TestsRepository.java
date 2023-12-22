package com.example.students_project.repository;

import com.example.students_project.entity.Tests;
import com.example.students_project.entity.TestsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestsRepository extends JpaRepository<Tests, Integer> {
    List<Tests> findAllByTestsInfo(TestsInfo testsInfo);
    Boolean deleteAllByTestsInfo(TestsInfo testsInfo);
}
