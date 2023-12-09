package com.bunsen.rfidbasedattendancesystem.controller;

import com.bunsen.rfidbasedattendancesystem.model.User;
import com.bunsen.rfidbasedattendancesystem.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserCardController {
    private final UserService userService;

    public UserCardController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/associateUserWithCard")
    public String associateUserWithCard(@RequestParam("cardNumber") String cardNumber,
                                        @RequestParam("userId") Long userId) throws MessagingException {
        if(cardNumber!=null && userId !=null){
        userService.associateUserWithCard(cardNumber, userId);
        return "redirect:/";
        }
        return "redirect:/associateUserWithCard"; // Redirect to the appropriate page
    }

    @GetMapping("/associateCardWithUser")
    public String showAssociateCardWithUserPage(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "associateCardWithUser";
    }
}
