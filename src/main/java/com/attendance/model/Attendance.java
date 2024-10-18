package com.attendance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime clockInTime; // Time of clock-in
    private LocalDateTime clockOutTime; // Time of clock-out
    private double workedHours; // Total hours worked in a day
    private double overtimeHours; // Overtime hours worked
    private double latitude; // For geofencing
    private double longitude; // For geofencing

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Foreign key relationship to User
}
