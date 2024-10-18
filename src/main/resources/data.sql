INSERT INTO attendance (userId, clockInTime, clockOutTime, workedHours, overtimeHours, latitude, longitude) VALUES (1, CURRENT_TIMESTAMP, NULL, 0, 0, 37.7749, -122.4194);
INSERT INTO leave (userId, leaveType, startDate, endDate, status) VALUES (1, 'VACATION', '2024-01-01', '2024-01-07', 'PENDING');
INSERT INTO overtime_request (userId, requestDate, hoursRequested, status) VALUES (1, CURRENT_TIMESTAMP, 5, 'PENDING');
