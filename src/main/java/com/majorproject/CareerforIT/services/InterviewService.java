package com.majorproject.CareerforIT.services;

import com.majorproject.CareerforIT.DTO.InterviewRequest;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.stereotype.Service;

@Service
public class InterviewService {


    private final MongoCollection<Document> collection;

    public InterviewService(MongoDatabase mongoDatabase) {
        this.collection = mongoDatabase.getCollection("mockInterview");
    }

    public String submitDetails (InterviewRequest interviewRequest){
        Document doc = new Document("mockId", interviewRequest.getMockId())
                .append("jsonMockResp", interviewRequest.getJsonMockResp())
                .append("jobPosition", interviewRequest.getJobPosition())
                .append("jobDesc", interviewRequest.getJobDesc())
                .append("jobExperience", interviewRequest.getJobExperience())
                .append("createdBy", interviewRequest.getCreatedBy())
                .append("createdAt", interviewRequest.getCreatedAt());

        try {
            // Insert the document into the MongoDB collection
            collection.insertOne(doc);
            // Log the inserted document for confirmation
            System.out.println("Inserted document: {}"+ doc.toJson());
            return "Details Submitted successfully";
        } catch (Exception e) {
            System.out.println("Error inserting document: "+ e);
            return "Failed to submit the details";
        }
    }

    public InterviewRequest getInterviewDetails(String mockId) {
        Document query = new Document("mockId", mockId);
        MongoCursor<Document> cursor = collection.find(query).iterator();

        if (cursor.hasNext()) {
            Document doc = cursor.next();
            // Map the result to InterviewRequest
            InterviewRequest interviewRequest = new InterviewRequest();
            interviewRequest.setMockId(doc.getString("mockId"));
            interviewRequest.setJsonMockResp(doc.getString("jsonMockResp"));
            interviewRequest.setJobPosition(doc.getString("jobPosition"));
            interviewRequest.setJobDesc(doc.getString("jobDesc"));
            interviewRequest.setJobExperience(doc.getString("jobExperience"));
            interviewRequest.setCreatedBy(doc.getString("createdBy"));
            interviewRequest.setCreatedAt(String.valueOf(doc.getDate("createdAt")));

            return interviewRequest;
        } else {
            return null;  // Or throw an exception if you want
        }
    }


}
