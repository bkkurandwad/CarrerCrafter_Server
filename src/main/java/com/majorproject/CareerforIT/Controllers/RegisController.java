package com.majorproject.CareerforIT.Controllers;

import com.majorproject.CareerforIT.DTO.Register;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/regis")
public class RegisController {

    private final MongoCollection<Document> collection;

    @Autowired
    public RegisController(MongoDatabase mongoDatabase) {
        this.collection = mongoDatabase.getCollection("StudentDetails");
    }

    @PostMapping("/student")
    public String registerStudent(@RequestBody Register reg) {
        Document doc = new Document("name", reg.getName())
                .append("email", reg.getEmail())
                .append("password", reg.getPassword())
                .append("year", reg.getYear())
                .append("branch", reg.getBranch())
                .append("phn_no", reg.getPhn_no());

        try {
            // Insert the document into the MongoDB collection
            collection.insertOne(doc);
            // Log the inserted document for confirmation
            System.out.println("Inserted document: {}"+ doc.toJson());
            return "Registration successful";
        } catch (Exception e) {
            System.out.println("Error inserting document: "+ e);
            return "Failed to register student";
        }
    }
}
