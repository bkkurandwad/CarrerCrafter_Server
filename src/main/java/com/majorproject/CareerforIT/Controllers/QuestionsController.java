package com.majorproject.CareerforIT.Controllers;

import com.majorproject.CareerforIT.DTO.QuestionRequest;
import com.majorproject.CareerforIT.DTO.QuestionResponse; // Import the response DTO
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/ques")
public class QuestionsController {

    private final MongoCollection<Document> questionsCollection;

    @Autowired
    public QuestionsController(MongoDatabase mongoDatabase) {
        this.questionsCollection = mongoDatabase.getCollection("questions"); // Adjust collection name if needed
    }

    @GetMapping("/codenowques")
    public QuestionResponse getQuestion(@RequestHeader("id") int id) {
        System.out.println(id);
        // Find the document with the specified questionID
        Document questionDoc = questionsCollection.find(new Document("questionID", id)).first();

        if (questionDoc != null && questionDoc.containsKey("fullQuestion") && questionDoc.containsKey("difficulty")) {
            // Extract fields
            String fullQuestion = questionDoc.getString("fullQuestion");
            String difficulty = questionDoc.getString("difficulty");

            // Return as QuestionResponse object
            return new QuestionResponse(id, fullQuestion, difficulty);
        } else {
            // Return a response indicating question was not found
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found");
        }
    }


    @GetMapping("/list")
    public List<QuestionResponse> getQuestions(@RequestHeader("level") int level) {
        List<QuestionResponse> questions = new ArrayList<>();
        System.out.println(level);
        System.out.println("Attempting to fetch questions from the database...");

        // Map the level to corresponding difficulty
        String difficulty = "";
        switch (level) {
            case 0:
                difficulty = "Easy";
                break;
            case 1:
                difficulty = "Medium";
                break;
            case 2:
                difficulty = "Hard";
                break;
            default:
                System.out.println("Invalid level received: " + level);
                return questions; // Return empty list if invalid level is received
        }

        try (MongoCursor<Document> cursor = questionsCollection.find(new Document("difficulty", difficulty)).iterator()) {
            if (!cursor.hasNext()) {
                System.out.println("No documents found in the questions collection for difficulty: " + difficulty);
            }

            while (cursor.hasNext()) {
                Document questionDoc = cursor.next();

                // Check if the fields exist in each document
                if (questionDoc.containsKey("questionID") && questionDoc.containsKey("fullQuestion") && questionDoc.containsKey("difficulty")) {
                    int questionID = questionDoc.getInteger("questionID");
                    String fullQuestion = questionDoc.getString("fullQuestion");
                    String questionDifficulty = questionDoc.getString("difficulty");

                    // Add the question to the response list
                    questions.add(new QuestionResponse(questionID, fullQuestion, questionDifficulty));
                    System.out.println("Added question: " + fullQuestion + " with difficulty: " + questionDifficulty);
                } else {
                    System.out.println("A document is missing expected fields.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Total questions retrieved for level " + difficulty + ": " + questions.size());
        return questions;
    }


}
