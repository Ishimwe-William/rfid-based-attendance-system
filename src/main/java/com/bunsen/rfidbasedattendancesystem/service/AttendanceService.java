package com.bunsen.rfidbasedattendancesystem.service;

import com.bunsen.rfidbasedattendancesystem.repository.AttendanceRepo;
import com.bunsen.rfidbasedattendancesystem.repository.model.Attendance;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {
    private AttendanceRepo repo;

    public AttendanceService(AttendanceRepo repo) {
        this.repo = repo;
    }

    public void saveData(Attendance attendance) {
        repo.save(attendance);
    }

    public List<Attendance> getAllAttendancesSortedByCreated() {
        Sort sortByCreatedDesc = Sort.by(Sort.Direction.DESC, "created");
        List<Attendance> attendances = repo.findAll(sortByCreatedDesc);
        return attendances;
    }

}