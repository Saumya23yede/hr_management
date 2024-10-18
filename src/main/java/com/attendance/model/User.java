package com.attendance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users") // Avoid conflict with SQL reserved keywords
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username; // Unique username for login

    @NotNull
    @Size(min = 2, max = 100)
    private String name;
    @Column(unique = true, nullable = false)
    private String email; // Unique email for login

    @Column(nullable = false)
    private String password; // Password (hashed)

    // A user can have multiple attendance records and leave requests
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attendance> attendances;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Leave> leaves;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payroll> payrolls;
    @NotNull
    @Column(name = "contact_number")
    private String contactNo;

    @NotNull
    private String dob;

    @Size(max = 255)
    private String address;

    @NotNull
    private int salary;

    // @Size(min = 12, max = 12, message = "Aadhar ID must be exactly 12 digits.")
    @NotNull
    private String aadharId;

    @NotNull
    @Column(name = "role")
    private Role role;

    // Storing resume file as binary data (Blob)
    @Lob
    @Basic(fetch = FetchType.LAZY) // Lazy loading to improve performance
    @Column(name = "resume")
    private byte[] resume;

    // Additional fields can be added as needed
}
