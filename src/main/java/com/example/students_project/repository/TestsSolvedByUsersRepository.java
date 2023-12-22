package com.example.students_project.repository;

import com.example.students_project.entity.TestsInfo;
import com.example.students_project.entity.TestsSolvedByUsers;
import com.example.students_project.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestsSolvedByUsersRepository extends JpaRepository<TestsSolvedByUsers, Integer> {
    Optional<TestsSolvedByUsers> findFirstByUsers(Users users);
    List<TestsSolvedByUsers> findAllByTestsInfo(TestsInfo testsInfo);
}
