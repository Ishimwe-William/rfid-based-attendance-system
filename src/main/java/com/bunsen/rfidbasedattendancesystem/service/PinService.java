package com.bunsen.rfidbasedattendancesystem.service;

import com.bunsen.rfidbasedattendancesystem.model.Pin;
import com.bunsen.rfidbasedattendancesystem.repository.PinRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PinService {
    private PinRepository pinRepository;

    public PinService(PinRepository pinRepository) {
        this.pinRepository = pinRepository;
    }

    public void savePin(Pin pinEntity) {
        // Retrieve the existing PIN for the email
        Pin existingPin = pinRepository.findByEmail(pinEntity.getEmail());

        // If an existing PIN is found, delete it
        if (existingPin != null) {
            pinRepository.delete(existingPin);
        }

        // Save the new PIN
        pinRepository.save(pinEntity);
    }

    public boolean isPinSent(String email) {
        Pin storedPinEntity = pinRepository.findByEmail(email);
        return storedPinEntity != null && !isPinExpired(storedPinEntity);
    }

    public boolean verifyPin(String email, String enteredPin) {
        // Retrieve the stored PIN entity from the database based on the email
        Pin storedPinEntity = pinRepository.findByEmail(email);

        // Check if a matching PIN entity is found and if the entered PIN is correct
        return storedPinEntity != null && storedPinEntity.getPinCode().equals(enteredPin)
                && !isPinExpired(storedPinEntity);
    }

    private boolean isPinExpired(Pin pinEntity) {
        // Check if the PIN has expired based on the expiration time
        return pinEntity.getExpirationTime().isBefore(LocalDateTime.now());
    }
}
