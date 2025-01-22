package com.majorproject.CareerforIT.services;

import com.majorproject.CareerforIT.DTO.AssesmentQuestionResponse;
import com.majorproject.CareerforIT.DTO.AssesmentResponse;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.client.MongoDatabase;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import com.mongodb.client.model.Filters;

@Service
public class AssesmentsService {

    private final MongoCollection<Document> assesmentsCollection; // Collection for assesments
    private final MongoCollection<Document> questionsCollection;   // Collection for Questions

    // Constructor initializing both collections
    public AssesmentsService(MongoDatabase mongoDatabase) {
        this.assesmentsCollection = mongoDatabase.getCollection("assesments"); // Assesments collection
        this.questionsCollection = mongoDatabase.getCollection("Questions"); // Questions collection
    }

    // Fetch all assesments
    public List<AssesmentResponse> getAssesments() {
        List<AssesmentResponse> responses = new ArrayList<>();

        try {
            Document query = new Document(); // Empty query to fetch all documents

            // Fetch all documents from the assesments collection
            for (Document resultDoc : assesmentsCollection.find(query)) {
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

    // Fetch questions for a specific assessment
    public List<AssesmentQuestionResponse> getQuestionsForAssessment(Integer assignId) {
        List<AssesmentQuestionResponse> questionList = new ArrayList<>();
        try {
            // Query the Questions collection for documents with the given assign_id
            List<Document> questionDocs = questionsCollection.find(Filters.eq("assign_id", assignId)).into(new ArrayList<>());

            // Iterate over each question document retrieved
            for (Document questionDoc : questionDocs) {
                AssesmentQuestionResponse questionResponse = new AssesmentQuestionResponse();
                questionResponse.setQuestionNo(questionDoc.getInteger("question_no"));
                questionResponse.setQuestion(questionDoc.getString("question"));
                questionResponse.setOptions((List<String>) questionDoc.get("options"));
                questionResponse.setCorrectAnswer(questionDoc.getString("correct_answer"));
                questionList.add(questionResponse);
            }

            if (questionList.isEmpty()) {
                throw new IllegalArgumentException("No questions found for assignId: " + assignId);
            }
        } catch (Exception e) {
            System.out.println("Error fetching questions: " + e.getMessage());
        }
        return questionList;
    }

}
