package com.bunsen.rfidbasedattendancesystem.service;

import com.bunsen.rfidbasedattendancesystem.repository.AttendanceRepo;
import com.bunsen.rfidbasedattendancesystem.model.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        List<Attendance>attendances = repo.findAll(sortByCreatedDesc);
        return attendances;
    }

    public Page<Attendance> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return repo.findPaginated(pageable);
    }

    public List<Attendance> getLatestRecords(int count) {
        Sort sortByCreatedDesc = Sort.by(Sort.Direction.DESC, "created");
        Pageable pageable = PageRequest.of(0, count, sortByCreatedDesc);
        return repo.findPaginated(pageable).getContent();
    }
    // Provide a default value for count to fetch all latest records
    public List<Attendance> getLatestRecords() {
        // Set a default count value or use Integer.MAX_VALUE to get all records
        return getLatestRecords(Integer.MAX_VALUE);
    }

    public Optional<Attendance> findById(String id) {
        return repo.findById(id);
    }
}