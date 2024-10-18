package com.attendance.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import com.attendance.model.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {

    @NotNull(message = "Username is required")
    @Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters")
    private String username;

    @NotNull(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotNull(message = "Contact number is required")
    @Size(min = 10, max = 15, message = "Contact number must be between 10 and 15 digits")
    private String contactNo;

    @NotNull(message = "Date of birth is required")
    private String dob;

    @Size(max = 255, message = "Address can be up to 255 characters")
    private String address;

    @NotNull(message = "Salary is required")
    private Integer salary;

    @NotNull(message = "Aadhar ID is required")
    @Size(min = 12, max = 12, message = "Aadhar ID must be exactly 12 digits")
    private String aadharId;

    @NotNull(message = "Job role is required")
    private Role jobRole;

    // This is used for resume file upload
    private MultipartFile resume;

}
