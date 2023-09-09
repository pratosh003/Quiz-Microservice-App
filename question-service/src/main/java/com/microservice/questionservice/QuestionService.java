package com.microservice.questionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    QuestionDAO questionDAO;

    public ResponseEntity<List<Question>> getALlQuestions(){
        try{
            return new ResponseEntity<>(questionDAO.findAll(), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try{
            return new ResponseEntity<>(questionDAO.findByCategory(category), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public String addQuestion(Question question) {
        questionDAO.save(question);
        return "success!";
    }

    public String saveOrUpdate(Question question) {
        questionDAO.save(question);
        return "Update Successful!";
    }

    public String deleteQuestion(Question question) {
        questionDAO.delete(question);
        return "deleted successfully";
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, Integer numOfQuestions) {
        List<Integer> questionsId = questionDAO.getRandomQuestionsByCategory(category, numOfQuestions);
        return new ResponseEntity<>(questionsId, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromIds(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        for(Integer id: questionIds){
            questions.add(questionDAO.findById(id).get());
        }

        for(Question q: questions){
            wrappers.add(new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(),
                    q.getOption2(), q.getOption3(), q.getOption4()));
        }
        return new ResponseEntity<>(wrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int result = 0;
        for(Response r: responses){
            Question question = questionDAO.findById(r.getId()).get();
            if(r.response.equals(question.getRightAnswer()))
                result++;
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
