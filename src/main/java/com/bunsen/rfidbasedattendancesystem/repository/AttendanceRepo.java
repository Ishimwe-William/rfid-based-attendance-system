package com.bunsen.rfidbasedattendancesystem.repository;

import com.bunsen.rfidbasedattendancesystem.model.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance,String> {
    @Query(value = "Select * from Attendance order by created desc",nativeQuery = true)
    Page<Attendance> findPaginated(Pageable pageable);
}
