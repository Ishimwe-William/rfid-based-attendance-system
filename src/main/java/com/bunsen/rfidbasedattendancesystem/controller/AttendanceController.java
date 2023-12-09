package com.bunsen.rfidbasedattendancesystem.controller;

import com.bunsen.rfidbasedattendancesystem.model.Attendance;
import com.bunsen.rfidbasedattendancesystem.model.Card;
import com.bunsen.rfidbasedattendancesystem.model.User;
import com.bunsen.rfidbasedattendancesystem.service.AttendanceService;
import com.bunsen.rfidbasedattendancesystem.service.CardService;
import com.bunsen.rfidbasedattendancesystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
public class AttendanceController {
    private final AttendanceService attendanceService;
    private final CardService cardService;
    private final UserService userService;
    private boolean arduinoConnected = false; // Flag to track Arduino connection status
    private long lastArduinoCommunicationTime = System.currentTimeMillis();
    private static final long MAX_INACTIVITY_PERIOD = 1000; // Maximum inactivity period in milliseconds (adjust as needed)
    private String temporaryCardNumber;

    @Autowired
    public AttendanceController(AttendanceService attendanceService, CardService cardService, UserService userService) {
        this.attendanceService = attendanceService;
        this.cardService = cardService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model) {
        return findPaginated(1, model);
    }

    @GetMapping("/home/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 10;
        updateConnectionStatus();

        // Convert arduinoConnected to a string attribute
        String connectionStatus = arduinoConnected ? "Connected" : "Not Connected";

        Page<Attendance> page = attendanceService.findPaginated(pageNo, pageSize);
        List<Attendance> attendances = page.getContent();

        model.addAttribute("arduinoConnected", connectionStatus);
        model.addAttribute("connectionColor", arduinoConnected ? "green" : "red");
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("attendances", attendances);
        return "index";
    }

//    @PostMapping("/sensor-data")
//    public String postData(@RequestParam("card_number") String card_no) {
//        // Convert card number to uppercase
//        String cardNo = card_no.toUpperCase();
//
//        // Check if a user with the given card number already exists
//        User existingUser = userService.findByCardNumber(cardNo);
//
//        if (existingUser == null) {
//            // If the user does not exist, check if a card with the same number exists
//            Card existingCard = cardService.findByCardNumber(cardNo);
//
//            if (existingCard == null) {
//                // If the card does not exist, create a new card and save it
//                Card newCard = new Card();
//                newCard.setCardNumber(cardNo);
//                cardService.save(newCard);
//            }
//        }
//            temporaryCardNumber = card_no.toUpperCase();
//
//            Attendance attendance = new Attendance();
//            attendance.setCardNo(card_no.toUpperCase());
//            attendanceService.saveData(attendance);
//            return "success";
//    }

    @PostMapping("/sensor-data")
    public String postData(@RequestParam("card_number") String card_no) {
        // Convert card number to uppercase
        String cardNo = card_no.toUpperCase();

        // Check if a user with the given card number already exists
        User existingUser = userService.findByCardNumber(cardNo);

        if (existingUser == null) {
            // If the user does not exist, check if a card with the same number exists
            Card existingCard = cardService.findByCardNumber(cardNo);

            if (existingCard == null) {
                // If the card does not exist, create a new card and save it
                Card newCard = new Card();
                newCard.setCardNumber(cardNo);
                cardService.save(newCard);

                // Save the ID of the newly created card in the Attendance record
                Attendance attendance = new Attendance();
                attendance.setCard(newCard); // Assuming you have a setCard method in the Attendance class
                attendanceService.saveData(attendance);
            } else {
                // If the card already exists, save the ID in the Attendance record
                Attendance attendance = new Attendance();
                attendance.setCard(existingCard);
                attendanceService.saveData(attendance);
            }
        } else {
            // If the user already exists, create a new Attendance record and associate it with the existing Card
            Attendance attendance = new Attendance();
            attendance.setCard(existingUser.getCard()); // Assuming User has a getCard() method
            attendanceService.saveData(attendance);
        }

        temporaryCardNumber = card_no.toUpperCase();
        return "success";
    }



    @PostMapping("/connection-status")
    @ResponseBody
    public String updateArduinoConnectionStatus(@RequestBody String status) {
        if ("connected".equalsIgnoreCase(status.trim())) {
            arduinoConnected = true;
            lastArduinoCommunicationTime = System.currentTimeMillis();
            return "Connection status updated: Connected";
        } else {
            arduinoConnected = false;
            return "Connection status updated: Not Connected";
        }
    }

    @GetMapping("/tapped-card")
    @ResponseBody
    public String sendCardNumberToForm() {
        return temporaryCardNumber != null ? temporaryCardNumber : "Tap you card";
    }
    private void updateConnectionStatus() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastArduinoCommunicationTime > MAX_INACTIVITY_PERIOD) {
            // The Arduino has not communicated for a while, mark it as not connected
            arduinoConnected = false;
        }
    }

    // New method for handling the AJAX request to get connection status
    @GetMapping("/connection-status")
    @ResponseBody
    public String getArduinoConnectionStatus() {
        updateConnectionStatus();
        return arduinoConnected ? "Connected" : "Not Connected";
    }
    @GetMapping("/home/latestRecords")
    @ResponseBody
    public List<Attendance> getLatestRecords() {
        List<Attendance> latestRecords = attendanceService.getLatestRecords();

        // Format the date for each attendance record
        latestRecords.forEach(attendance -> {
            String formattedDate = DateTimeFormatter.ofPattern("EEE dd MMM, yyyy HH:mm:ss").format(attendance.getCreated());
            attendance.setFormattedDate(formattedDate);
        });

        return latestRecords;
    }


    @GetMapping("/home/showFormForView/{id}")
    public String showFormForView(@PathVariable("id") String id, Model model) {
        Optional<Attendance> attendance = attendanceService.findById(id);

        if (!attendance.isPresent()) {
            // Handle the case where the attendance record with the specified ID is not found
            return "error";
        }

        model.addAttribute("attendance", attendance.get());
        return "showFormForView";
    }

}
