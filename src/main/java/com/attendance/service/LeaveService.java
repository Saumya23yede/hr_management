package com.attendance.service;

import com.attendance.model.Leave;
import com.attendance.model.User;
import com.attendance.repository.LeaveRepository;
import com.attendance.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveService {
    @Autowired
    private LeaveRepository leaveRepository;
    @Autowired
    private UserRepository userRepository;

    public Leave requestLeave(Long userId, Leave leaveDetails) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        leaveDetails.setUser(user);
        leaveDetails.setStatus("PENDING");

        return leaveRepository.save(leaveDetails);
    }

    public List<Leave> getUsersWithPendingLeave() {
        return leaveRepository.findByStatus("PENDING");
    }

    public void acceptLeaveRequest(Long leaveRequestId) {
        Leave leaveRequest = leaveRepository.findById(leaveRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid leave request ID"));
        leaveRequest.setStatus("APPROVED");
        leaveRepository.save(leaveRequest);
    }

    public void rejectLeaveRequest(Long leaveRequestId) {
        Leave leaveRequest = leaveRepository.findById(leaveRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid leave request ID"));
        leaveRequest.setStatus("REJECTED");
        leaveRepository.save(leaveRequest);
    }
    // Other methods for leave management can be added as needed
}
