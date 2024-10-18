package com.attendance.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.attendance.form.EmployeeForm;
import com.attendance.model.Employee;

public interface EmployeeService {
    Employee createEmployee(EmployeeForm employeeForm);

    Employee getEmployeeById(String empId);

    List<Employee> getAllEmployees();

    Employee updateEmployee(String empId, EmployeeForm employeeForm);

    void deleteEmployee(String empId);

    Page<Employee> getAllEmployees(Pageable pageable);
}
