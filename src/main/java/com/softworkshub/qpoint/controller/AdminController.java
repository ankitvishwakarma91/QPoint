package com.softworkshub.qpoint.controller;


import com.softworkshub.qpoint.model.FeedBack;
import com.softworkshub.qpoint.service.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private QuestionServiceImpl questionService;


    @GetMapping("/profile")
    public String adminProfile(Model model) {
        return "adminprofile";
    }

    @GetMapping("/get-feedback-ui")
    public String getFeedback(Model model){
        List<FeedBack> feedback = questionService.getFeedback();
        model.addAttribute("feedbacks", feedback);
        return "adminfeedbackui";
    }


}
