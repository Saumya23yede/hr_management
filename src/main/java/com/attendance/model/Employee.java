package com.attendance.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.Basic;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "employees")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Employee {

    @Id
    @NotNull
    @Column(name = "employee_id", unique = true, nullable = false)
    private String empId;

    @NotNull
    @Size(min = 2, max = 100)
    private String name;

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
    private Long aadharId;

    @NotNull
    @Column(name = "job_role")
    @Enumerated
    private Role jobRole;

    // Storing resume file as binary data (Blob)
    @Lob
    @Basic(fetch = FetchType.LAZY) // Lazy loading to improve performance
    @Column(name = "resume")
    private byte[] resume;

    // One-to-Many relationship with Certification

}
