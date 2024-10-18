package com.attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attendance.model.Payroll;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {

}
