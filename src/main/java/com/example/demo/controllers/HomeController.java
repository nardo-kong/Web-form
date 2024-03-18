package com.example.demo.controllers;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Class.User;

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
            return "intertemporal_choice_page_1";
        } else {
            model.addAttribute("error", "Invalid account");
            return "Home";
        }
    }

    @PostMapping("/next_page")
    public String nextPage(@RequestParam int type, int page, Model model) {
        model.addAttribute("type", type);
        if (page == 1) {
            return "intertemporal_choice_page_2";
        } else if (page == 2) {
            return "intertemporal_choice_page_3";
        } else {
            model.addAttribute("error", "Invalid page");
            return "intertemporal_choice_page_1";
        }
    }
    
}
