package com.microservice.quizservice;

import lombok.Data;

@Data
public class Quizdto {
    String category;
    int numOfQuestions;
    String title;
}
