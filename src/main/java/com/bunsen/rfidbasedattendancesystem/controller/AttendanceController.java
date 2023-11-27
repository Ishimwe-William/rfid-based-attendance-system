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

    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }
    @GetMapping("/")
    public String index(Model model) {
        List<Attendance> data = attendanceService.getAllAttendancesSortedByCreated();
        model.addAttribute("attendances", data);
        return "index";
    }

    @PostMapping("/sensor-data")
    public String postData(@RequestParam("card_number") String card_no) {
        Attendance attendance = new Attendance();

        if (card_no != null) {
            attendance.setCardNo(card_no);
            attendanceService.saveData(attendance);
            return "success";
        } else {
            return "error";
        }
    }
}
