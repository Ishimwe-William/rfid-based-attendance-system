package com.bunsen.rfidbasedattendancesystem.repository;

import com.bunsen.rfidbasedattendancesystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}

