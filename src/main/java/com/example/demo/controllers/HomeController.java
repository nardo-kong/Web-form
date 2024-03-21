package com.example.demo.controllers;
import com.example.demo.Entities.User;
import com.example.demo.Repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home() {
        return "Home";
    }

    @PostMapping("/login")
    public String login(@RequestParam String accountId, Model model) {
        User user = userRepository.findByAccountId(accountId);
        if (user != null ) {
            int lastDigit = Character.getNumericValue(accountId.charAt(accountId.length() - 1));
            if (lastDigit % 2 == 0) {
                model.addAttribute("type", 0);
            } else {
                model.addAttribute("type", 1);
            }
            model.addAttribute("page", 1);
            model.addAttribute("scale", "intertemporal choice");
            return "/Form";
        } else {
            model.addAttribute("error", "Invalid account");
            return "Home";
        }
    }
    
}
