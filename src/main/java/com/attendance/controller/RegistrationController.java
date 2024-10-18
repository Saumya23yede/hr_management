package com.attendance.controller;

import com.attendance.model.User;
import com.attendance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Returns the register.html template
    }

    @PostMapping("/api/auth/register")
    public String registerUser(User user) {
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.save(user); // Implement save method in UserService
        return "redirect:/login"; // Redirect to login page after registration
    }

    @GetMapping("/login")
    public String loginpage() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String userDashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("username", userDetails.getUsername());
        System.out.println(userDetails.getUsername());
        // Pass the username to the model
        model.addAttribute("role", userService.getUserRoleByUsername(userDetails.getUsername()).get().toString());
        System.out.println(userService.getUserRoleByUsername(userDetails.getUsername()).get());
        return "dashboard"; // Return the name of the Thymeleaf template
    }
}
