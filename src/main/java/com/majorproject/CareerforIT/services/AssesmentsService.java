package com.majorproject.CareerforIT.services;

import com.majorproject.CareerforIT.DTO.AssesmentQuestionResponse;
import com.majorproject.CareerforIT.DTO.AssesmentResponse;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.client.MongoDatabase;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssesmentsService {

    private final MongoCollection<Document> collection;

    public AssesmentsService(MongoDatabase mongoDatabase) {
        this.collection = mongoDatabase.getCollection("assesments");
    }

    public List<AssesmentResponse> getAssesments(){
        List<AssesmentResponse> responses = new ArrayList<>();

        try {
            // Create a query filter to find all matching documents
            Document query = new Document();

            // Fetch all matching documents
            for (Document resultDoc : collection.find(query)) {
                AssesmentResponse showres = new AssesmentResponse();

                showres.setId(resultDoc.getInteger("assign_id"));
                showres.setName(resultDoc.getString("name"));
                showres.setDesc(resultDoc.getString("desc"));
                showres.setTotalQues(resultDoc.getInteger("total_ques"));

                responses.add(showres);
            }

            return responses;

        } catch (Exception e) {
            System.out.println("Error fetching assignments: " + e.getMessage());
            return new ArrayList<>(); // Return an empty list instead of null for better handling
        }
    }

    public List<AssesmentQuestionResponse> getQuestionsForAssessment(int assignId) {
        List<AssesmentQuestionResponse> questionResponses = new ArrayList<>();

        try {
            // Create a query to find the questions for the given assignment ID
            Document query = new Document("assign_id", assignId);

            // Fetch all matching documents
            for (Document resultDoc : collection.find(query)) {
                AssesmentQuestionResponse questionResponse = new AssesmentQuestionResponse();

                questionResponse.setQuestionNo(resultDoc.getInteger("question_no"));
                questionResponse.setQuestion(resultDoc.getString("question"));
                questionResponse.setOptions((List<String>) resultDoc.get("options"));
                questionResponse.setCorrectAnswer(resultDoc.getString("correct_answer"));

                questionResponses.add(questionResponse);
            }

            return questionResponses;

        } catch (Exception e) {
            System.out.println("Error fetching questions: " + e.getMessage());
            return new ArrayList<>(); // Return an empty list if there is an error
        }
    }

}

