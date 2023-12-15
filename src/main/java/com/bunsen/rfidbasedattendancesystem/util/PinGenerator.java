package com.bunsen.rfidbasedattendancesystem.util;

import java.util.Random;

public class PinGenerator {
    public static String generatePin() {
        // Generate a 6-digit random PIN
        Random random = new Random();
        int pin = 100000 + random.nextInt(900000);
        return String.valueOf(pin);
    }
}

