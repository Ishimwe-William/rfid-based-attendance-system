package com.bunsen.rfidbasedattendancesystem.repository;

import com.bunsen.rfidbasedattendancesystem.repository.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance,String> {
}
