package com.majorproject.CareerforIT.DTO;

public class QuestionResponse {
    private int questionID;         // New field for question ID
    private String fullQuestion;
    private String difficulty;

    public QuestionResponse(int questionID, String fullQuestion, String difficulty) {
        this.questionID = questionID;
        this.fullQuestion = fullQuestion;
        this.difficulty = difficulty;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getFullQuestion() {
        return fullQuestion;
    }

    public void setFullQuestion(String fullQuestion) {
        this.fullQuestion = fullQuestion;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
