package com.example.demo.controllers;
import com.example.demo.Repositories.QuestionRepository;
import com.example.demo.Entities.Question;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class QuestionnaireController {

    @Autowired
    private QuestionRepository questionRepository; // 假设你有一个QuestionRepository来从数据库中获取问题

    @GetMapping("/questionnaire")
    public ModelAndView questionnaire(@RequestParam int type, int page, String scale) {
        ModelAndView modelAndView = new ModelAndView("questionnaire");

        // 将type, page, scale添加到模型中
        modelAndView.addObject("type", type);
        modelAndView.addObject("page", page);
        modelAndView.addObject("scale", scale);

        // 从数据库中获取问题，然后添加到模型中
        List<Question> questions = questionRepository.findByScale(scale);
        modelAndView.addObject("questions", questions);

        return modelAndView;
    }
}