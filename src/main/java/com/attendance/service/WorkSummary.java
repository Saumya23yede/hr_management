package com.attendance.service;

public class WorkSummary {
    private double totalWorkedHours;
    private int totalDays;

    public WorkSummary(double totalWorkedHours, int totalDays) {
        this.totalWorkedHours = totalWorkedHours;
        this.totalDays = totalDays;
    }

    // Getters
    public double getTotalWorkedHours() {
        return totalWorkedHours;
    }

    public int getTotalDays() {
        return totalDays;
    }
}
