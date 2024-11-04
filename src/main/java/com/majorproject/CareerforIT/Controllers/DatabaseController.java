package com.majorproject.CareerforIT.Controllers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("DBOps")
public class DatabaseController {

    // Constants for MongoDB connection
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "Career_Crafter";

    // Instance variables for MongoDB client and database
    private MongoClient mongoClient;
    private MongoDatabase database;

    // Endpoint to connect to the database
    @PostMapping("/connect")
    public String connect() {
        try {
            mongoClient = MongoClients.create(CONNECTION_STRING); // Create the client
            database = mongoClient.getDatabase(DATABASE_NAME);   // Get the database
            return "Connected to the database: " + DATABASE_NAME; // Success message
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            return "Failed to connect to the database"; // Error message
        }
    }

    // Optional cleanup method to close the connection
    @PostMapping("close")
    public String closeConnection() {
        if (mongoClient != null) {
            mongoClient.close(); // Close the client connection
            return "Closed connection"; // Success message
        }
        return "No connection to close"; // Message if no connection exists
    }
}
