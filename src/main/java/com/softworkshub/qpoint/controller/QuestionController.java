package com.softworkshub.qpoint.controller;

import com.softworkshub.qpoint.model.Question;
import com.softworkshub.qpoint.model.QuestionWrapper;
import com.softworkshub.qpoint.service.QuestionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    private QuestionServiceImpl questionService;



    @GetMapping("/home")
    public String homePage() {
        return "index";
    }

    @PostMapping("/addAll")
    public ResponseEntity<String> addAllQuestion(@RequestBody QuestionWrapper questions){
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
            @RequestParam(value = "image" , required = false) MultipartFile image,
            @RequestParam String subject,
            @RequestParam String term,
            @RequestParam Integer year
    ) throws IOException {

        String imageUrl = null;

        if (image != null && !image.isEmpty()) {
            String originalFilename = image.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            String uniqueImageName = "img_" + UUID.randomUUID() + extension;



            File savedFile = new ClassPathResource("/static/img").getFile();

            Path path = Paths.get(savedFile.getAbsolutePath() , uniqueImageName);

            System.out.println(path);
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            imageUrl = "img/" + uniqueImageName;
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
            @RequestParam(value = "pageSize",defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo
    ) {
        log.info("getQuestionSubjectAndYear - subject: {}, year: {}", subject, year);
        long totalCount = questionService.countBySubjectAndYear(subject, year);
        int totalPage = (int) Math.ceil((double) totalCount/pageSize);
        model.addAttribute("totalPages", (int) Math.ceil((double) totalCount / pageSize));
        model.addAttribute("pageNo", pageNo);
        List<Question> question = questionService.getQuestion(subject, year,pageSize,pageNo);
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

}
