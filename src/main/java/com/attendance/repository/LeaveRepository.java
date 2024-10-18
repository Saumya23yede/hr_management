package com.attendance.repository;

import com.attendance.model.Leave;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRepository extends JpaRepository<Leave, Long> {
    List<Leave> findByStatus(String status);
}
