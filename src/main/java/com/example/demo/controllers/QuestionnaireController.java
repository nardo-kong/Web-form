package com.example.demo.controllers;
import com.example.demo.Repositories.QuestionRepository;
import com.example.demo.Repositories.ScaleRepository;
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
    private ScaleRepository scaleRepository;

    @GetMapping("/questionnaire")
    public ModelAndView questionnaire(@RequestParam int group_type, int page
    , Long scaleId, @RequestParam(required = true) String accountId
    , @RequestParam(required = true) AnswerRecord answerRecord, String error) {

        ModelAndView modelAndView = new ModelAndView("Form");

        // Add type, page, scale to the model
        Scale scale = scaleRepository.findById(scaleId).orElse(null);
        int totalPage = scale.getTotalPage();
        
        String bgColor;
        String music;
        if (group_type == 0) {
            bgColor = scale.getCGroupBgColor();
            music = scale.getCGroupMusic();
        } else {
            bgColor = scale.getEGroupBgColor();
            music = scale.getEGroupMusic();
        }
            
        addModelAttributes(modelAndView, group_type, bgColor, music, page, totalPage, scaleId, accountId, answerRecord, error);

        // Get questions from the database and add to the model
        List<Question> questions = questionRepository.findByScaleAndPage(scale, page);
        modelAndView.addObject("questions", questions);

        return modelAndView;
    }

    @PostMapping("/next_page")
    public String submitAnswers(@RequestParam Map<String, String> answers, @RequestParam(required = true) Long answerRecordId
            , @RequestParam int group_type, @RequestParam int page, @RequestParam Long scaleId
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
            addRedirectAttributes(redirectAttributes, savedPage + 1, group_type, scaleId, accountId, answerRecord, error);
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
        Scale scale = scaleRepository.findById(scaleId).orElse(null);
        int totalPage = scale.getTotalPage();

        if (page == totalPage) {
            answerService.completeAnswerRecord(answerRecord);
            return "Home";
        } else {
            addRedirectAttributes(redirectAttributes, page + 1, group_type, scaleId, accountId, answerRecord, null);
            return "redirect:/questionnaire";
        }
        

    }

    // Some method
    private void addModelAttributes(ModelAndView modelAndView, int group_type, String bgColor, String music, int page, int totalPage, Long scaleId, String accountId, AnswerRecord answerRecord, String error) {
        String scaleName = scaleRepository.findById(scaleId).orElse(null).getTitle();
        modelAndView.addObject("group_type", group_type);
        modelAndView.addObject("bgColor", bgColor);
        modelAndView.addObject("music", music);
        modelAndView.addObject("page", page);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("scaleId", scaleId);
        modelAndView.addObject("scale", scaleName);
        modelAndView.addObject("answerRecordId", answerRecord.getId());
        modelAndView.addObject("accountId", accountId);
        if (error != null) {
            modelAndView.addObject("error", error);
        }
    }

    private void addRedirectAttributes(RedirectAttributes redirectAttributes, int page, int group_type, Long scaleId, String accountId, AnswerRecord answerRecord, String error) {
        String scaleName = scaleRepository.findById(scaleId).orElse(null).getTitle();
        redirectAttributes.addAttribute("page", page);
        redirectAttributes.addAttribute("group_type", group_type);
        redirectAttributes.addAttribute("scaleId", scaleId);
        redirectAttributes.addAttribute("scale", scaleName);
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
        answers.remove("scaleId");
        answers.remove("accountId");
    }

    /*
    @PostMapping("/next_page")
    public String postMethodName() {
        return "Home";
    }
    */

    
}