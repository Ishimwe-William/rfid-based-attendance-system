package com.bunsen.rfidbasedattendancesystem.util;

import com.bunsen.rfidbasedattendancesystem.repository.PinRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PinCleanupTask {

    private PinRepository pinRepository; // Assuming you have a PinRepository for database operations

    public PinCleanupTask(PinRepository pinRepository) {
        this.pinRepository = pinRepository;
    }

    @Scheduled(fixedRate = 60000) // Run every minute
    public void cleanupExpiredPins() {
        LocalDateTime now = LocalDateTime.now();
        pinRepository.deleteExpiredPins(now);
    }
}

