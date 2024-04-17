package com.example.demo.controllers;
import com.example.demo.Repositories.QuestionRepository;
import com.example.demo.Repositories.ScaleRepository;
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
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class QuestionnaireController {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private AnswerRecordService answerRecordService;
    @Autowired
    private ScaleRepository ScaleRepository;

    @GetMapping("/questionnaire")
    public ModelAndView questionnaire(@RequestParam int group_type, int page, String scale, String accountId) {

        // 创建AnswerRecord对象
        AnswerRecord answerRecord = new AnswerRecord();
        answerRecord.setStartTimestamp(new Date());
        answerRecordService.saveAnswerRecord(answerRecord,scale,accountId);

        ModelAndView modelAndView;
        if (page == 1) {
            modelAndView = new ModelAndView("temp_page_first");
        } else {
            modelAndView = new ModelAndView("temp_page_first");
        
        }

        // 将type, page, scale添加到模型中
        int totalPage = ScaleRepository.findByTitle(scale).getTotalPage();
        modelAndView.addObject("group_type", group_type);
        modelAndView.addObject("page", page);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("scale", scale);
        modelAndView.addObject("answerRecordId", answerRecord.getId());
        modelAndView.addObject("accountId", accountId);

        // 从数据库中获取问题，然后添加到模型中
        List<Question> questions = questionRepository.findByScaleAndPage(scale, page);
        modelAndView.addObject("questions", questions);

        return modelAndView;
    }

    @PostMapping("/next_page")
    public String submitAnswers(@RequestParam Map<String, String> answers, @RequestParam(required = false) Long answerRecordId
            , @RequestParam String group_type, @RequestParam int page, @RequestParam String scale, @RequestParam String accountId) {
        
        // Remove non-answers parameters from the map
        answers.remove("answerRecordId");
        answers.remove("group_type");
        answers.remove("page");
        answers.remove("totalPage");
        answers.remove("scale");
        answers.remove("accountId");
        
        AnswerRecord answerRecord = answerRecordService.findById(answerRecordId);
        if (answerRecord == null) {
            return "redirect:/error";
        }

        for (Map.Entry<String, String> entry : answers.entrySet()) {
            String questionId = entry.getKey().replaceFirst("answer", "");
            String answerContent = entry.getValue();

            Question question = questionRepository.findById(Integer.parseInt(questionId)).orElse(null);

            answerService.saveAnswers(answerRecord, question, answerContent);
        }

        answerService.completeAnswerRecord(answerRecord);

        // 重定向到下一页
        int totalPage = ScaleRepository.findByTitle(scale).getTotalPage();
        if (page == totalPage) {
            return "Home";
        } else {
            return "redirect:/questionnaire?group_type=" + group_type + "&page=" + (page + 1) + "&scale=" + scale + "&accountId=" + accountId;
        }
        

    }

    /*
    @PostMapping("/next_page")
    public String postMethodName() {
        return "Home";
    }
    */

    
}