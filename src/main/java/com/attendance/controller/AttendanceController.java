package com.attendance.controller;

import com.attendance.model.Attendance;
import com.attendance.model.CustomUserDetails; // Import your CustomUserDetails
import com.attendance.model.Payroll;
import com.attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;
    private Attendance attendance;

    @PostMapping("/clock-in")
    public Attendance clockIn(Authentication authentication, @RequestParam double latitude,
            @RequestParam double longitude) {
        // Change User to CustomUserDetails
        System.out.println(latitude + "============" + longitude);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal(); // Correct casting
        this.attendance = attendanceService.clockIn(userDetails.getUser().getId(), latitude, longitude);
        return this.attendance;
    }

    @PostMapping("/clock-out")
    public Attendance clockOut() {

        return attendanceService.clockOut(attendance.getId());
    }

}
