package com.attendance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.attendance.form.UserForm;
import com.attendance.model.Payroll;
import com.attendance.model.User;
import com.attendance.service.AttendanceService;
import com.attendance.service.UserService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Show form to create a new user
    @GetMapping("/create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "create"; // Return the view name for creating a user
    }

    // Handle user creation
    @PostMapping("/create")
    public String createUser(@ModelAttribute UserForm userForm) {
        userForm.setPassword(passwordEncoder.encode(userForm.getPassword()));
        userService.createUser(userForm);
        return "redirect:/users"; // Redirect to the user list after creation
    }

    // Get user details by ID
    @GetMapping("/{userId}")
    public String getUser(@PathVariable Long userId, Model model) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "details"; // Return the view name for user details
    }

    // Get list of users with pagination
    @GetMapping
    public String getAllUsers(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size, Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userService.getAllUsers(pageable);

        model.addAttribute("users", userPage);
        System.out.println("user==========================================================");
        return "list"; // Return the view name for the list of users
    }

    // Show form to edit user
    @GetMapping("/{userId}/edit")
    public String showEditUserForm(@PathVariable Long userId, Model model) {
        User user = userService.getUserById(userId);

        model.addAttribute("user", user);
        return "edit"; // Return the view name for editing a user
    }

    // Handle user update
    @PostMapping("/{userId}/edit")
    public String updateUser(@PathVariable Long userId, @ModelAttribute UserForm userForm) {
        System.out.println(userForm.getJobRole());
        userService.updateUser(userId, userForm);

        return "redirect:/users"; // Redirect to the user list after update
    }

    // Handle user deletion
    @PostMapping("/{userId}/delete")
    public String deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return "redirect:/users"; // Redirect to the user list after deletion
    }

    // Show form to upload certifications for user
    @GetMapping("/{userId}/certifications/upload")
    public String showUploadCertificationsForm(@PathVariable Long userId, Model model) {
        model.addAttribute("userId", userId);
        return "upload"; // Return the view name for uploading certifications
    }

    @GetMapping("{userId}/payroll-slip")
    public String getPayrollSlip(@PathVariable Long userId, Model model) {
        // Fetch payroll data based on attendance records
        Payroll payroll = attendanceService.generatePayrollForUser(userId);

        // Add payroll data to the model
        model.addAttribute("payroll", payroll);
        model.addAttribute("employeeName", payroll.getUser().getName());
        model.addAttribute("totalWorkedHours", payroll.getTotalEarnings());
        model.addAttribute("netPay", payroll.getNetPay());
        model.addAttribute("payroll", payroll);

        return "payroll-slip"; // Thymeleaf template for displaying payroll slip
    }
}
