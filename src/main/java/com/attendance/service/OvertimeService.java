package com.attendance.service;

import com.attendance.model.OvertimeRequest;
import com.attendance.repository.OvertimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OvertimeService {
    @Autowired
    private OvertimeRepository overtimeRepository;

    // Method to request overtime
    public OvertimeRequest requestOvertime(OvertimeRequest request) {
        request.setStatus("PENDING");
        return overtimeRepository.save(request);
    }

    // Method to approve overtime
    public void approveOvertime(Long requestId) {
        Optional<OvertimeRequest> requestOpt = overtimeRepository.findById(requestId);
        if (requestOpt.isPresent()) {
            OvertimeRequest request = requestOpt.get();
            request.setStatus("APPROVED");
            overtimeRepository.save(request);
        }
    }

    // Method to reject overtime
    public void rejectOvertime(Long requestId) {
        Optional<OvertimeRequest> requestOpt = overtimeRepository.findById(requestId);
        if (requestOpt.isPresent()) {
            OvertimeRequest request = requestOpt.get();
            request.setStatus("REJECTED");
            overtimeRepository.save(request);
        }
    }
}
