package com.attendance.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.attendance.form.EmployeeForm;
import com.attendance.model.Employee;
import com.attendance.repository.EmployeeRepo;
import com.attendance.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepo employeeRepo) {

        this.employeeRepo = employeeRepo;
    }

    @Override
    public Employee createEmployee(EmployeeForm employeeForm) {
        Employee employee = mapFormToEntity(employeeForm);
        return employeeRepo.save(employee);
    }

    @Override
    public Employee getEmployeeById(String empId) {
        return employeeRepo.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    @Override
    public Employee updateEmployee(String empId, EmployeeForm employeeForm) {
        Employee employee = getEmployeeById(empId);
        // Update fields based on the EmployeeForm
        employee.setName(employeeForm.getName());
        employee.setContactNo(employeeForm.getContactNo());
        employee.setDob(employeeForm.getDob());
        employee.setAddress(employeeForm.getAddress());
        employee.setSalary(employeeForm.getSalary());
        employee.setAadharId(employeeForm.getAadharId());
        employee.setJobRole(employeeForm.getJobRole());

        // Handle resume upload (if necessary)
        if (employeeForm.getResume() != null && !employeeForm.getResume().isEmpty()) {
            try {
                employee.setResume(employeeForm.getResume().getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Error saving resume file", e);
            }
        }

        // Save the updated employee
        return employeeRepo.save(employee);
    }

    @Override
    public void deleteEmployee(String empId) {
        employeeRepo.deleteById(empId);
    }

    private Employee mapFormToEntity(EmployeeForm employeeForm) {
        Employee employee = new Employee();
        employee.setEmpId(employeeForm.getEmpId());
        employee.setName(employeeForm.getName());
        employee.setContactNo(employeeForm.getContactNo());
        employee.setDob(employeeForm.getDob());
        employee.setAddress(employeeForm.getAddress());
        employee.setSalary(employeeForm.getSalary());
        employee.setAadharId(employeeForm.getAadharId());
        employee.setJobRole(employeeForm.getJobRole());

        // Handle resume upload
        if (employeeForm.getResume() != null && !employeeForm.getResume().isEmpty()) {
            try {
                employee.setResume(employeeForm.getResume().getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Error saving resume file", e);
            }
        }

        return employee;
    }

    @Override
    public Page<Employee> getAllEmployees(Pageable pageable) {
        return employeeRepo.findAll(pageable);
    }
}
