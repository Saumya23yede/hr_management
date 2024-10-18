package com.attendance.repository;

import com.attendance.model.OvertimeRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OvertimeRepository extends JpaRepository<OvertimeRequest, Long> {
}
