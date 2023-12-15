package com.bunsen.rfidbasedattendancesystem.repository;

import com.bunsen.rfidbasedattendancesystem.model.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PinRepository extends JpaRepository<Pin, Long> {

    @Modifying
    @Query("DELETE FROM Pin p WHERE p.expirationTime <= :now")
    void deleteExpiredPins(LocalDateTime now);

    Pin findByEmail(String email);

    @Query("SELECT p FROM Pin p WHERE p.email = :email")
    List<Pin> findPinsByEmail(String email);

    @Modifying
    @Query("DELETE FROM Pin p WHERE p.email = :email")
    void deleteByEmail(String email);
}
