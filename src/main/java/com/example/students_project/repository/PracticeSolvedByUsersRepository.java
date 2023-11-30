package com.example.students_project.repository;

import com.example.students_project.entity.PracticeSolvedByUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PracticeSolvedByUsersDtoRepository extends JpaRepository<PracticeSolvedByUsers, Integer> {
}
