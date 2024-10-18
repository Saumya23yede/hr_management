package com.attendance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Reference to the User

    private LocalDate payrollDate;
    private double basicSalary; // Base salary for the employee
    private double overtimePay;
    private double bonus;
    private double taxDeduction;
    private double otherDeductions;
    private double totalEarnings;
    private double totalDeductions;
    private double netPay; // Total net pay after deductions
}
