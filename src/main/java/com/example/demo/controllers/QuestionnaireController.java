package com.example.demo.controllers;
import com.example.demo.Repositories.QuestionRepository;
import com.example.demo.Repositories.OptionRepository;
import com.example.demo.Services.AnswerService;
import com.example.demo.Services.AnswerRecordService;
import com.example.demo.Entities.Answer;
import com.example.demo.Entities.Question;
import com.example.demo.Entities.Scale;
import com.example.demo.Entities.User;
import com.example.demo.Entities.Option;
import com.example.demo.Entities.AnswerRecord;


import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class QuestionnaireController {

    @Autowired
    private QuestionRepository questionRepository;
    private OptionRepository optionRepository;
    private AnswerService answerService;
    private AnswerRecordService answerRecordService;

    @GetMapping("/questionnaire")
    public ModelAndView questionnaire(@RequestParam int group_type, int page, String scale) {

        ModelAndView modelAndView;
        if (page == 1) {
            modelAndView = new ModelAndView("temp_page_first");
        } else {
            modelAndView = new ModelAndView("temp_page_others");
        
        }

        // 将type, page, scale添加到模型中
        modelAndView.addObject("group_type", group_type);
        modelAndView.addObject("page", page);
        modelAndView.addObject("scale", scale);

        // 从数据库中获取问题，然后添加到模型中
        List<Question> questions = questionRepository.findByScale(scale);
        modelAndView.addObject("questions", questions);

        return modelAndView;
    }

    @PostMapping("/submit")
    public String submitAnswers(@RequestParam Map<Question, Option> answers, @RequestParam Long answerRecordId
            , @RequestParam String group_type, @RequestParam int page, @RequestParam String scale) {
        
        AnswerRecord answerRecord = answerRecordService.findById(answerRecordId);
        if (answerRecord == null) {
            return "redirect:/error";
        }

        for (Map.Entry<Question, Option> entry : answers.entrySet()) {
            Question question = entry.getKey();
            Option option = entry.getValue();
            answerService.saveAnswers(answerRecord, question, option);
        }

        answerService.completeAnswerRecord(answerRecord);

        // 重定向到下一页
        return "redirect:/questionnaire?group_type=" + group_type + "&page=" + (page + 1) + "&scale=" + scale;
    }

    @PostMapping("/next_page")
    public String postMethodName() {
        return "Home";
    }
    

    
}