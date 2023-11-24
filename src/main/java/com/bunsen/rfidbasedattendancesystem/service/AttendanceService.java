package com.bunsen.rfidbasedattendancesystem.service;

import com.bunsen.rfidbasedattendancesystem.repository.AttendanceRepo;
import com.bunsen.rfidbasedattendancesystem.repository.model.Attendance;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {
    private AttendanceRepo repo;

    public AttendanceService(AttendanceRepo repo) {
        this.repo = repo;
    }

    public void saveData(Attendance attendance) {
        repo.save(attendance);
    }

}
