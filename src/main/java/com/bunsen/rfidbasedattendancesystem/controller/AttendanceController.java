package com.bunsen.rfidbasedattendancesystem.controller;

import com.bunsen.rfidbasedattendancesystem.repository.model.Attendance;
import com.bunsen.rfidbasedattendancesystem.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AttendanceController {
    private AttendanceService attendanceService;
    private boolean arduinoConnected = false; // Flag to track Arduino connection status
    private long lastArduinoCommunicationTime = System.currentTimeMillis();
    private static final long MAX_INACTIVITY_PERIOD = 1000; // Maximum inactivity period in milliseconds (adjust as needed)

    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Attendance> data = attendanceService.getAllAttendancesSortedByCreated();
        model.addAttribute("attendances", data);
        updateConnectionStatus();

        // Convert arduinoConnected to a string attribute
        String connectionStatus = arduinoConnected ? "Connected" : "Not Connected";

        model.addAttribute("arduinoConnected", connectionStatus);
        model.addAttribute("connectionColor", arduinoConnected ? "green" : "red");
        return "index";
    }


    @PostMapping("/sensor-data")
    public String postData(@RequestParam("card_number") String card_no) {
        Attendance attendance = new Attendance();
        if (card_no != null) {
            attendance.setCardNo(card_no.toUpperCase());
            attendanceService.saveData(attendance);
            return "success";
        } else {
            return "error";
        }
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
}
