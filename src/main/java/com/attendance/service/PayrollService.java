package com.attendance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendance.model.Attendance;
import com.attendance.model.Payroll;
import com.attendance.model.User;
import com.attendance.repository.PayrollRepository;
import com.attendance.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Service
public class PayrollService {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    private PayrollRepository payrollRepository;

    // Fetch user attendance data and calculate payroll based on attendance
    public Payroll calculatePayroll(Long userId, List<Attendance> attendanceList) {
        double totalWorkedHours = 0;
        double totalOvertimeHours = 0;

        // Iterate over attendance records to calculate total hours and overtime
        for (Attendance attendance : attendanceList) {
            totalWorkedHours += attendance.getWorkedHours();
            totalOvertimeHours += attendance.getOvertimeHours();
        }

        // Assuming basic salary and overtime rate for simplicity
        double basicSalary = userRepository.findSalaryById(userId); // Can be fetched from User/Employee details
        double overtimeRate = 20; // Example overtime rate

        double overtimePay = totalOvertimeHours * overtimeRate;
        double totalEarnings = basicSalary + overtimePay;
        double taxDeduction = calculateTax(totalEarnings);
        double otherDeductions = 100; // Example other deductions

        double totalDeductions = taxDeduction + otherDeductions;
        double netPay = totalEarnings - totalDeductions;

        // Create payroll record
        Payroll payroll = new Payroll();
        payroll.setUser(getUserById(userId).get()); // Fetch user details from the service
        LocalDateTime currentDateTime = LocalDateTime.now();
        payroll.setPayrollDate(currentDateTime.toLocalDate());

        payroll.setBasicSalary(basicSalary);
        payroll.setOvertimePay(overtimePay);
        payroll.setTotalEarnings(totalEarnings);
        payroll.setTaxDeduction(taxDeduction);
        payroll.setOtherDeductions(otherDeductions);
        payroll.setTotalDeductions(totalDeductions);
        payroll.setNetPay(netPay);

        return payrollRepository.save(payroll);
    }

    // Method to calculate tax (this is a placeholder, actual tax calculation can
    // vary)
    private double calculateTax(double totalEarnings) {
        return totalEarnings * 0.10; // 10% tax example
    }

    // Dummy method to simulate fetching user by ID (this should come from
    // UserService)
    private Optional<User> getUserById(Long userId) {
        // Implement this method to fetch user details from a repository or service
        return userRepository.findById(userId);
    }
}
