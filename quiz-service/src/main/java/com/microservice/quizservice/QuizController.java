package com.microservice.quizservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    QuizService quizService;

    @PostMapping("create")
    ResponseEntity<String> createQuiz(@RequestBody Quizdto quizdto){
        return quizService.createQuestions(quizdto.getCategory(), quizdto.getNumOfQuestions(), quizdto.getTitle());
    }

    @GetMapping("get/{id}")
    ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
        return quizService.getQuestions(id);
    }

    @PostMapping("submit/{quizId}")
    ResponseEntity<Integer> submitQuiz(@PathVariable Integer quizId, @RequestBody List<Response> responses){
        return quizService.calculateResult(responses);
    }
}
