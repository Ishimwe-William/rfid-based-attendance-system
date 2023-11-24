package com.bunsen.rfidbasedattendancesystem.controller;

import com.bunsen.rfidbasedattendancesystem.repository.model.Attendance;
import com.bunsen.rfidbasedattendancesystem.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AttendanceController {
    private AttendanceService attendanceService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }
    @RequestMapping(path = "/")
    public String index(){
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
