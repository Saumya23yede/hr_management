package com.attendance.repository;

import com.attendance.model.Attendance;
import com.attendance.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByUser(User user);

    List<Attendance> findByUserId(Long userId);
}
