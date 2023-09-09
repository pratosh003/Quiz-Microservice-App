package com.microservice.quizservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDAO quizDAO;
    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuestions(String category, int numQ, String title) {
        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDAO.save(quiz);

        return new ResponseEntity<>("quiz created!", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestions(Integer id) {
        Optional<Quiz> quiz = quizDAO.findById(id);
        List<Integer> questionIds = quiz.get().questionIds;
        ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestions(questionIds);

        return questions;
    }

    public ResponseEntity<Integer> calculateResult(List<Response> responses) {
        return quizInterface.getScore(responses);
    }
}
