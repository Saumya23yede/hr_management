package com.attendance.controller;

import com.attendance.model.OvertimeRequest;
import com.attendance.service.OvertimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/overtime")
public class OvertimeController {
    @Autowired
    private OvertimeService overtimeService;

    @PostMapping("/request")
    public OvertimeRequest requestOvertime(@RequestBody OvertimeRequest request) {
        return overtimeService.requestOvertime(request);
    }

    @PutMapping("/approve/{requestId}")
    public void approveOvertime(@PathVariable Long requestId) {
        overtimeService.approveOvertime(requestId);
    }

    @PutMapping("/reject/{requestId}")
    public void rejectOvertime(@PathVariable Long requestId) {
        overtimeService.rejectOvertime(requestId);
    }
}
