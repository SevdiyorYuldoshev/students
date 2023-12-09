package com.example.students_project.repository;

import com.example.students_project.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    @Query(value = "select exists (select 1 from users where username = ?1)", nativeQuery = true)
    boolean usernameExists(String username);

    Optional<Users> findFirstByUsername(String username);

//    @Query(value = "select * from users u where u.id = ?1 and u.activity = ?2", nativeQuery = true)
    Optional<Users> findByIdAndActivityEquals(Integer id, int activity);
}
