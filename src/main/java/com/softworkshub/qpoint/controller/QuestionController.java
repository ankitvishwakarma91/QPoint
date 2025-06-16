package com.softworkshub.qpoint.controller;

import com.softworkshub.qpoint.model.FeedBack;
import com.softworkshub.qpoint.model.Question;
import com.softworkshub.qpoint.model.QuestionWrapper;
import com.softworkshub.qpoint.model.UserEntity;
import com.softworkshub.qpoint.service.ImageUploadService;
import com.softworkshub.qpoint.service.QuestionServiceImpl;
import com.softworkshub.qpoint.service.UserService;
import com.softworkshub.qpoint.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.random.RandomGenerator;

//@RestController
//@RequestMapping("/q")
@Controller
public class QuestionController {

    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);


    @Autowired
    private ImageUploadService imageUploadService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private QuestionServiceImpl questionService;

    @ModelAttribute
    public void getUserDetails(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            UserEntity userByUsername = userService.getUserByUsername(username);
            model.addAttribute("user", userByUsername);
            String username1 = userByUsername.getUsername();
            model.addAttribute("username", username1);
        }
    }

    @GetMapping("/home")
    public String homePage(Principal principal, Model model) {
        return "index";
    }

    @PostMapping("/addAll")
    public ResponseEntity<String> addAllQuestion(@RequestBody QuestionWrapper questions) {
        List<Question> questions1 = questions.getQuestions();
        questionService.addAllQuestions(questions1);
        return ResponseEntity.ok("All questions added successfully!");
    }

    @GetMapping("/addQuestion")
    public String addPage() {
//        model.addAttribute("question", new Question());
        return "add";
    }


    @PostMapping("/save")
    public String getQuestions(
            @RequestParam String question,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam String subject,
            @RequestParam String term,
            @RequestParam Integer year
    ) throws IOException {

        String imageUrl = null;

        if (image != null && !image.isEmpty()) {
            // this is for local
//            String originalFilename = image.getOriginalFilename();
//            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//
//            String uniqueImageName = "img_" + UUID.randomUUID() + extension;
//
//
//
//            File savedFile = new ClassPathResource("/static/img").getFile();
//
//            Path path = Paths.get(savedFile.getAbsolutePath() , uniqueImageName);
//
//            System.out.println(path);
//            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//
//            imageUrl = "img/" + uniqueImageName;

            // this is for upload image on cloud the service name is  cloudinary
            imageUrl = imageUploadService.uploadImage(image);
        }


        Question q = new Question();
        q.setQuestion(question);
        q.setSubject(subject);
        q.setTerm(term);
        q.setYear(year);
        q.setImageUrl(imageUrl);
        questionService.addQuestion(q);
        return "redirect:/addQuestion";
    }

    @GetMapping("/questions")
    public String getQuestionSubjectAndYear(
            Model model,
            @RequestParam String subject,
            @RequestParam Integer year,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo
    ) {
        log.info("getQuestionSubjectAndYear - subject: {}, year: {}", subject, year);
        long totalCount = questionService.countBySubjectAndYear(subject, year);
        int totalPage = (int) Math.ceil((double) totalCount / pageSize);
        model.addAttribute("totalPages", (int) Math.ceil((double) totalCount / pageSize));
        model.addAttribute("pageNo", pageNo);
        List<Question> question = questionService.getQuestion(subject, year, pageSize, pageNo);
        model.addAttribute("questions", question);
        model.addAttribute("subject", subject);
        model.addAttribute("year", year);
        model.addAttribute("totalPage", totalPage);
        return "question";
    }


    //Test Purpose
//    @GetMapping("/questions/json")
//    public ResponseEntity<List<Question>> getQuestionJson(
//            @RequestParam String subject,
//            @RequestParam Integer year,
//            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
//            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo
//    ) {
//        List<Question> question = questionService.getQuestion(subject, year, pageSize, pageNo);
//        return ResponseEntity.ok(question);
//    }


    @PostMapping("/addSingle")
    public ResponseEntity<Question> addSingleQuestionUsingApi(@RequestBody Question question) {
        Question question1 = questionService.addSingleQuestionUsingApi(question);
        return new ResponseEntity<>(question1, HttpStatus.CREATED);
    }

    @PostMapping("/submit-feedback")
    public String feedBackSave(
            @Valid @ModelAttribute FeedBack feedBack,
            BindingResult result,
            HttpSession session,
            Model model
    ) {
        if (result.hasErrors()) {
            return "feedbackForm";
        }

        FeedBack feedBack1 = questionService.addFeedback(feedBack);
        model.addAttribute("feedback", feedBack1);
        session.setAttribute("success", "🎉 Thank you for your feedback!");

//        FeedBack feedBack1 = questionService.addFeedback(feedBack);
//        if (feedBack1 != null) {
//            session.setAttribute("success", "Thank You for your feedback!");
//        }else {
//            session.setAttribute("error", "Something went wrong!");
//        }
        return "redirect:/feedback";
    }

    @GetMapping("/feedback")
    public String feedbackPage(Model model) {
        model.addAttribute("feedBack", new FeedBack());
        return "feedbackForm";
    }




}
