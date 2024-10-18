package com.attendance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.attendance.form.EmployeeForm;
import com.attendance.model.Employee;
import com.attendance.service.EmployeeService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/create")
    public String showCreateEmployeeForm(Model model) {
        model.addAttribute("employeeForm", new EmployeeForm());
        return "create"; // Return the view name for creating an employee
    }

    @PostMapping("/create")
    public String createEmployee(@ModelAttribute EmployeeForm employeeForm) {
        employeeService.createEmployee(employeeForm);
        return "redirect:/employees"; // Redirect to the employee list after creation
    }

    @GetMapping("/{empId}")
    public String getEmployee(@PathVariable String empId, Model model) {
        Employee employee = employeeService.getEmployeeById(empId);
        model.addAttribute("employee", employee);
        return "details"; // Return the view name for employee details
    }

    @GetMapping
    public String getAllEmployees(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size, Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employeePage = employeeService.getAllEmployees(pageable);
        model.addAttribute("employees", employeePage);
        return "list"; // Return the view name for the list of employees
    }

    @GetMapping("/{empId}/edit")
    public String showEditEmployeeForm(@PathVariable String empId, Model model) {
        Employee employee = employeeService.getEmployeeById(empId);
        EmployeeForm employeeForm = new EmployeeForm();
        employeeForm.setEmpId(employee.getEmpId());
        employeeForm.setName(employee.getName());
        employeeForm.setContactNo(employee.getContactNo());
        employeeForm.setDob(employee.getDob());
        employeeForm.setAddress(employee.getAddress());
        employeeForm.setSalary(employee.getSalary());
        employeeForm.setAadharId(employee.getAadharId());
        employeeForm.setJobRole(employee.getJobRole());
        model.addAttribute("employeeForm", employeeForm);
        return "edit"; // Return the view name for editing an employee
    }

    @PostMapping("/{empId}/edit")
    public String updateEmployee(@PathVariable String empId, @ModelAttribute EmployeeForm employeeForm) {
        employeeService.updateEmployee(empId, employeeForm);
        return "redirect:/employees"; // Redirect to the employee list after update
    }

    @PostMapping("/{empId}/delete")
    public String deleteEmployee(@PathVariable String empId) {
        employeeService.deleteEmployee(empId);
        return "redirect:/employees"; // Redirect to the employee list after deletion
    }

    @GetMapping("/{empId}/certifications/upload")
    public String showUploadCertificationsForm(@PathVariable String empId, Model model) {
        model.addAttribute("empId", empId);
        return "upload"; // Return the view name for uploading certifications
    }

}
