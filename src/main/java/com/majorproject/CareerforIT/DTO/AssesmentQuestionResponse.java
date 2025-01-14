package com.majorproject.CareerforIT.DTO;

import lombok.Data;

import java.util.List;

@Data
public class AssesmentQuestionResponse {

    private int questionNo;
    private String question;
    private List<String> options;
    private String correctAnswer;

}
