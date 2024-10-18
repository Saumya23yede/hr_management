package com.attendance.service;

import com.attendance.model.Attendance;
import com.attendance.model.Payroll;
import com.attendance.model.User;
import com.attendance.repository.AttendanceRepository;
import com.attendance.repository.UserRepository;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PayrollService payrollService;

    // Fetch attendance records for a user
    public List<Attendance> getAttendanceByUserId(Long userId) {
        return attendanceRepository.findByUserId(userId);
    }

    // Method to clock in a user
    public Attendance clockIn(Long userId, double latitude, double longitude) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setClockInTime(LocalDateTime.now());
        attendance.setLatitude(latitude);
        attendance.setLongitude(longitude);

        return attendanceRepository.save(attendance);
    }

    // Method to clock out a user
    public Attendance clockOut(Long attendanceId) {

        Optional<Attendance> attendanceOpt = attendanceRepository.findById(attendanceId);

        if (attendanceOpt.isPresent()) {
            Attendance attendance = attendanceOpt.get();
            attendance.setClockOutTime(LocalDateTime.now());
            System.out.println(attendance.getLatitude() + "---------------------------------------------------"
                    + LocalDateTime.now());
            calculateWorkedHours(attendance); // Calculate worked hours
            return attendanceRepository.save(attendance);
        }
        return null; // Handle appropriately
    }

    // Calculate worked hours and overtime
    private void calculateWorkedHours(Attendance attendance) {
        if (attendance.getClockInTime() != null && attendance.getClockOutTime() != null) {
            Duration workedDuration = Duration.between(attendance.getClockInTime(), attendance.getClockOutTime());
            double workedHours = workedDuration.toHours();
            System.out.println(workedHours + "============================================================");
            attendance.setWorkedHours(workedHours);

            // Assuming standard working hours are 8 hours
            if (workedHours > 8) {
                double overtimeHours = workedHours - 8;
                attendance.setOvertimeHours(overtimeHours);
            } else {
                attendance.setOvertimeHours(0);
            }
        }
    }

    // Method to calculate payroll for a user based on attendance
    public Payroll generatePayrollForUser(Long userId) {
        List<Attendance> attendanceList = getAttendanceByUserId(userId);
        return payrollService.calculatePayroll(userId, attendanceList);
    }

    public WorkSummary calculateTotalWorkHoursAndDays(Long userId) {
        List<Attendance> attendanceList = attendanceRepository.findByUserId(userId);

        double totalWorkedHours = 0.0;
        int totalDays = attendanceList.size();

        for (Attendance attendance : attendanceList) {
            totalWorkedHours += attendance.getWorkedHours() + attendance.getOvertimeHours();
        }

        return new WorkSummary(totalWorkedHours, totalDays);
    }
    // Other methods can be added as needed
}
