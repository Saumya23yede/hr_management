package com.attendance.form;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import com.attendance.model.Role;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeForm {

    @NotNull
    @Size(min = 2, max = 100, message = "Employee ID must be between 2 and 100 characters.")
    private String empId;

    @NotNull
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters.")
    private String name;

    @NotNull
    @Size(min = 10, max = 15, message = "Contact number must be between 10 and 15 characters.")
    private String contactNo;

    @NotNull
    private String dob;

    // @Size(max = 255, message = "Address can't be longer than 255 characters.")
    private String address;

    @NotNull
    private int salary;

    // @Size(min = 12, max = 12, message = "Aadhar ID must be exactly 12 digits.")
    @NotNull
    private Long aadharId;

    @NotNull
    private Role jobRole;

    // Resume file upload
    private MultipartFile resume;

    // List of certification IDs or names (optional for creation)
    // private List<String> certificationIds;
}
