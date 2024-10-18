package com.attendance.repository;

import com.attendance.model.Role;
import com.attendance.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email); // Optional: To find user by email

    Page<User> findAll(Pageable pageable);

    @Query("SELECT u.salary FROM User u WHERE u.id = :id")
    Integer findSalaryById(Long id);

    @Query("SELECT u.role FROM User u WHERE u.username = :username")
    Optional<Role> findRoleByUsername(@Param("username") String username); // Return an Optional<Role>

}
