package com.example.students_project.repository;

import com.example.students_project.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    Image findFirstByUsers_Id(Integer userId);
}
