package com.bunsen.rfidbasedattendancesystem.repository;

import com.bunsen.rfidbasedattendancesystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
