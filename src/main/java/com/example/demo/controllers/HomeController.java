package com.example.demo.controllers;
import com.example.demo.Entities.Scale;
import com.example.demo.Entities.User;
import com.example.demo.Repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home() {
        return "Home";
    }

    @PostMapping("/login")
    public String login(@RequestParam String accountId, Model model, RedirectAttributes redirectAttributes) {
        User user = userRepository.findByAccountId(accountId);
        if (user != null ) {
            int lastDigit = Character.getNumericValue(accountId.charAt(accountId.length() - 1));
            if (lastDigit % 2 == 0) {
                redirectAttributes.addAttribute("group_type", 0);
            } else {
                redirectAttributes.addAttribute("group_type", 1);
            }
            redirectAttributes.addAttribute("page", 1);

            String scale = user.getScale();
            redirectAttributes.addAttribute("scale", scale);
            redirectAttributes.addAttribute("accountId", accountId);
            return "redirect:/questionnaire";
        } else {
            model.addAttribute("error", "Invalid account");
            return "Home";
        }
    }
    
}
