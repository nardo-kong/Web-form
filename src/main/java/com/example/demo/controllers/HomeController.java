package com.example.demo.controllers;

import com.example.demo.Entities.AnswerRecord;
import com.example.demo.Entities.Scale;
import com.example.demo.Entities.User;
import com.example.demo.Repositories.ScaleRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Services.AnswerRecordService;

import java.util.Date;
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
    @Autowired
    private AnswerRecordService answerRecordService;
    @Autowired
    private ScaleRepository scaleRepository;

    @GetMapping("/")
    public String home() {
        return "Home";
    }

    @PostMapping("/login")
    public String login(@RequestParam String accountId, Model model, RedirectAttributes redirectAttributes) {
        User user = userRepository.findByAccountId(accountId);
        String error = null;

        if (user != null ) {

            int lastDigit = Character.getNumericValue(accountId.charAt(accountId.length() - 1));
            int groupType;
            if (lastDigit % 2 == 0) {
                groupType = 0;
            } else {
                groupType = 1;
            }

            // 选择Scale
            Long scaleId = user.getScaleId();
            Scale scale = scaleRepository.findById(scaleId).orElse(null);
            while (scale != null && scale.isDeleted()) {
                scale = scaleRepository.findByPreviousId(scaleId);
                scaleId = scale.getId();
                error = "The previous questionnaire has been edited, redirected to the latest version.";
            }

            // 创建AnswerRecord对象
            AnswerRecord answerRecord = new AnswerRecord();
            answerRecord.setStartTimestamp(new Date());
            answerRecord = answerRecordService.saveAnswerRecord(answerRecord,scale,accountId);

            // 添加重定向属性
            redirectAttributes.addAttribute("page", 1);
            redirectAttributes.addAttribute("group_type", groupType);
            redirectAttributes.addAttribute("scaleId", scaleId);
            redirectAttributes.addAttribute("accountId", accountId);
            redirectAttributes.addAttribute("answerRecord", answerRecord);
            redirectAttributes.addAttribute("error", error);

            return "redirect:/questionnaire";

        } else {
            model.addAttribute("error", "Invalid account");
            return "Home";
        }
    }
    
}
