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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class QuestionnaireController {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private AnswerRecordService answerRecordService;
    @Autowired
    private ScaleRepository ScaleRepository;

    @GetMapping("/questionnaire")
    public ModelAndView questionnaire(@RequestParam int group_type, int page
    , String scale, @RequestParam(required = true) String accountId
    , @RequestParam(required = true) AnswerRecord answerRecord, String error) {

        ModelAndView modelAndView = new ModelAndView("temp_page_first");

        // Add type, page, scale to the model
        int totalPage = ScaleRepository.findByTitle(scale).getTotalPage();
        addModelAttributes(modelAndView, group_type, page, totalPage, scale, accountId, answerRecord, error);

        // Get questions from the database and add to the model
        List<Question> questions = questionRepository.findByScaleAndPage(scale, page);
        modelAndView.addObject("questions", questions);

        return modelAndView;
    }

    @PostMapping("/next_page")
    public String submitAnswers(@RequestParam Map<String, String> answers, @RequestParam(required = true) Long answerRecordId
            , @RequestParam String group_type, @RequestParam int page, @RequestParam String scale
            , @RequestParam String accountId, RedirectAttributes redirectAttributes) {


        // Find the answer record
        AnswerRecord answerRecord = answerRecordService.findById(answerRecordId);
        if (answerRecord == null) {
            return "redirect:/error";
        }


        // Check if the current page has been completed
        int savedPage = answerRecord.getCurrentpage();
        if (savedPage != page - 1) {
            String error = savedPage > page - 1 ? "You have already completed this page." : "Please complete the previous page first.";
            addRedirectAttributes(redirectAttributes, savedPage + 1, group_type, scale, accountId, answerRecord, error);
            return "redirect:/questionnaire";
        }
        
        // Remove non-answers parameters from the map
        removeNonAnswerParameters(answers);
        
        
        // Iterate over the answers and save to the database
        for (Map.Entry<String, String> entry : answers.entrySet()) {
            String questionId = entry.getKey().replaceFirst("answer", "");
            String answerContent = entry.getValue();

            Question question = questionRepository.findById(Integer.parseInt(questionId)).orElse(null);

            answerService.saveAnswers(answerRecord, question, answerContent);
        }

        // Save the current page
        answerRecordService.savePage(answerRecord, page);
        

        // Redirect to the next page
        int totalPage = ScaleRepository.findByTitle(scale).getTotalPage();
        if (page == totalPage) {
            answerService.completeAnswerRecord(answerRecord);
            return "Home";
        } else {
            addRedirectAttributes(redirectAttributes, page + 1, group_type, scale, accountId, answerRecord, null);
            return "redirect:/questionnaire";
        }
        

    }

    // Some method
    private void addModelAttributes(ModelAndView modelAndView, int group_type, int page, int totalPage, String scale, String accountId, AnswerRecord answerRecord, String error) {
        modelAndView.addObject("group_type", group_type);
        modelAndView.addObject("page", page);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("scale", scale);
        modelAndView.addObject("answerRecordId", answerRecord.getId());
        modelAndView.addObject("accountId", accountId);
        if (error != null) {
            modelAndView.addObject("error", error);
        }
    }

    private void addRedirectAttributes(RedirectAttributes redirectAttributes, int page, String group_type, String scale, String accountId, AnswerRecord answerRecord, String error) {
        redirectAttributes.addAttribute("page", page);
        redirectAttributes.addAttribute("group_type", group_type);
        redirectAttributes.addAttribute("scale", scale);
        redirectAttributes.addAttribute("accountId", accountId);
        redirectAttributes.addAttribute("answerRecord", answerRecord);
        if (error != null) {
            redirectAttributes.addAttribute("error", error);
        }
    }

    private void removeNonAnswerParameters(Map<String, String> answers) {
        answers.remove("answerRecordId");
        answers.remove("group_type");
        answers.remove("page");
        answers.remove("totalPage");
        answers.remove("scale");
        answers.remove("accountId");
    }

    /*
    @PostMapping("/next_page")
    public String postMethodName() {
        return "Home";
    }
    */

    
}