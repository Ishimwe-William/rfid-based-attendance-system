//package com.bunsen.rfidbasedattendancesystem.controller;
//
//import com.bunsen.rfidbasedattendancesystem.model.Pin;
//import com.bunsen.rfidbasedattendancesystem.service.EmailService;
//import com.bunsen.rfidbasedattendancesystem.service.PinService;
//import com.bunsen.rfidbasedattendancesystem.util.PinGenerator;
//import jakarta.mail.MessagingException;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.time.LocalDateTime;
//
//@Controller
//public class PinController {
//    private EmailService emailService;
//    private final PinService pinService;
//
//    public PinController(EmailService emailService, PinService pinService) {
//        this.emailService = emailService;
//        this.pinService = pinService;
//    }
//
//    @GetMapping("/home/test")
//    public String showTestPage(Model model) {
//        // You can add any additional data to the model if needed
//        return "pinVerificationTest";
//    }
//
////    @PostMapping("/home/send-pin")
////    public String sendPinConfirmation(@RequestParam String email, Model model) throws MessagingException {
////        // Generate a pin code
////        String pinCode = PinGenerator.generatePin();
////
////        // Save the pin and its expiration time in your database (or cache)
////        // Here, we assume that you have a PinEntity class with fields like pinCode, email, and expirationTime
////
////        // Create a PinEntity and save it to your database
////        Pin pinEntity = new Pin();
////        pinEntity.setPinCode(pinCode);
////        pinEntity.setEmail(email);
////        pinEntity.setExpirationTime(LocalDateTime.now().plusMinutes(5));
////
////        // Save the pinEntity to your database
////        pinService.savePin(pinEntity);
////
////        // Send pin confirmation email
////        emailService.prepareAndSendEmail(email, pinCode);
////
////        // Pass data to the Thymeleaf template, if needed
////        model.addAttribute("email", email);
////
////        // Return the name of the Thymeleaf template without extension
////        return "pinConfirmationTemplate";
////    }
////
////    @PostMapping("/register/send-pin")
////    public String sendPin(@RequestParam String email, Model model) throws MessagingException {
////        // Generate a pin code
////        String pinCode = PinGenerator.generatePin();
////
////        // Save the pin and its expiration time in your database (or cache)
////        // Here, we assume that you have a PinEntity class with fields like pinCode, email, and expirationTime
////
////        // Create a PinEntity and save it to your database
////        Pin pinEntity = new Pin();
////        pinEntity.setPinCode(pinCode);
////        pinEntity.setEmail(email);
////        pinEntity.setExpirationTime(LocalDateTime.now().plusMinutes(5));
////
////        // Save the pinEntity to your database
////        pinService.savePin(pinEntity);
////
////        // Send pin confirmation email
////        emailService.prepareAndSendEmail(email, pinCode);
////
////        // Pass data to the Thymeleaf template, if needed
////        model.addAttribute("email", email);
////
////        // Redirect to the pin verification form
////        return "pinVerificationForm";
////    }
//
//    @PostMapping("/verifyPin")
//    public String verifyPin(@RequestParam String email, @RequestParam String pin, Model model) {
//        // Perform the verification logic
//        boolean isPinValid = pinService.verifyPin(email, pin);
//
//        // Add attributes to the model for displaying the result in the template
//        model.addAttribute("email", email);
//        model.addAttribute("isPinValid", isPinValid);
//
//        // Return the name of the Thymeleaf template for displaying the result
//        return "test";
//    }
//}
//
