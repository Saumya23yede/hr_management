package com.attendance.controller;

import com.attendance.model.CustomUserDetails;
import com.attendance.model.Leave;
import com.attendance.model.User;
import com.attendance.service.LeaveService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller // Change RestController to Controller
@RequestMapping("/api/leave")
public class LeaveController {
    @Autowired
    private LeaveService leaveService;

    @PostMapping("/request")
    public String requestLeave(@ModelAttribute Leave leave, Model model, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Leave savedLeave = leaveService.requestLeave(userDetails.getUser().getId(), leave); // Save leave request
        model.addAttribute("message", "Leave request submitted successfully: " + savedLeave.getLeaveType());
        return "dashboard"; // Redirect back to the leave application form with a success message
    }

    // Method to display the leave application form
    @GetMapping("/apply")
    public String showLeaveApplicationForm(Model model) {
        model.addAttribute("leave", new Leave());
        return "dashboard"; // Returns the leave_application.html template
    }

    @GetMapping("/pending")
    public String getPendingLeaveRequests(Model model) {
        List<Leave> pendingRequests = leaveService.getUsersWithPendingLeave();

        // Add the list of pending requests to the model to pass to the view
        model.addAttribute("pendingRequests", pendingRequests);

        // Return the name of the Thymeleaf template (e.g.,
        // "pending-leave-requests.html")
        return "leavepage";
    }

    // POST endpoint to accept a leave request
    @PostMapping("/accept")
    public String acceptLeaveRequest(@RequestParam("leaveRequestId") Long leaveRequestId) {
        leaveService.acceptLeaveRequest(leaveRequestId);
        return "redirect:/leave/pending"; // Redirect back to the pending requests page after action
    }

    // POST endpoint to reject a leave request
    @PostMapping("/reject")
    public String rejectLeaveRequest(@RequestParam("leaveRequestId") Long leaveRequestId) {
        leaveService.rejectLeaveRequest(leaveRequestId);
        return "redirect:/leave/pending"; // Redirect back to the pending requests page after action
    }
}
