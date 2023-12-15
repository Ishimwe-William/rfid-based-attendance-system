package com.bunsen.rfidbasedattendancesystem.controller;

import com.bunsen.rfidbasedattendancesystem.model.Pin;
import com.bunsen.rfidbasedattendancesystem.model.User;
import com.bunsen.rfidbasedattendancesystem.model.UserDto;
import com.bunsen.rfidbasedattendancesystem.service.EmailService;
import com.bunsen.rfidbasedattendancesystem.service.PinService;
import com.bunsen.rfidbasedattendancesystem.service.UserService;
import com.bunsen.rfidbasedattendancesystem.util.PinGenerator;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

// AuthController.java
@Controller
public class AuthController {

    private final UserService userService;
    private final EmailService emailService;
    private final PinService pinService;

    public AuthController(UserService userService, EmailService emailService, PinService pinService) {
        this.userService = userService;
        this.emailService = emailService;
        this.pinService = pinService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/send-pin")
    public String sendPin(@Valid @ModelAttribute("user") UserDto userDto,
                          BindingResult result,
                          Model model) throws MessagingException {
        String newUserEmail = userDto.getEmail();
        User existingUser = userService.findUserByEmail(newUserEmail);

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "register";
        }

        // Generate a pin code
        String pinCode = PinGenerator.generatePin();

        // Save the pin and its expiration time in your database (or cache)
        Pin pinEntity = new Pin();
        pinEntity.setPinCode(pinCode);
        pinEntity.setEmail(newUserEmail);
        pinEntity.setExpirationTime(LocalDateTime.now().plusMinutes(5));
        pinService.savePin(pinEntity);

        // Send pin confirmation email
        emailService.prepareAndSendEmail(newUserEmail, pinCode);

        // Pass data to the Thymeleaf template
        model.addAttribute("email", newUserEmail);
        model.addAttribute("pinSent", true);

        // Redirect to the PIN verification form
        return "pinVerificationForm";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserDto userDto,
                           BindingResult result,
                           @RequestParam String pin,
                           Model model) throws MessagingException {
        // Perform the pin verification logic
        boolean isPinValid = pinService.verifyPin(userDto.getEmail(), pin);

        if (!isPinValid) {
            // If pin is not valid, display an error
            model.addAttribute("pinError", "Invalid PIN. Please check your email and enter the correct PIN.");
            model.addAttribute("user", userDto);
            return "register";  // Redirect to the registration form with an error message
        }

        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "register";
        }

        userService.saveUser(userDto);

        //send email
        emailService.prepareAndSendEmail(userDto.getEmail(), "RBAS Admin",
                "WELCOME TO RBAS",
                "Thanks for Registering to RFID Based Attendance System");
        emailService.prepareAndSendEmail("bunsenplus.org@gmail.com", "RBAS Admin",
                "New Registered User",
                "Email: " + userDto.getEmail() + "\nDate: " + new Date());

        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}

