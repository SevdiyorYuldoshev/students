package com.example.students_project.repository;

import com.example.students_project.entity.PracticeSolvedByUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PracticeSolvedByUsersRepository extends JpaRepository<PracticeSolvedByUsers, Integer> {

    Boolean existsByPracticeId(Integer integer);

    Long countAllByPracticeId(Integer id);
    Long countAllByUsersId(Integer id);
    Optional<PracticeSolvedByUsers> findFirstById(Integer id);
}
