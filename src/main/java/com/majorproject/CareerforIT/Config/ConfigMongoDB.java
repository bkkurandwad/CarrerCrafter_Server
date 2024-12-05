package com.majorproject.CareerforIT.Config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigMongoDB {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "Career_Crafter";

    // Instance variable for MongoDB client
    private MongoClient mongoClient;

    // Bean for MongoDatabase
    @Bean
    public MongoDatabase mongoDatabase() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(CONNECTION_STRING); // Create the client if not already created
        }
        return mongoClient.getDatabase(DATABASE_NAME);        // Return the database instance
    }

    // Optional cleanup method to close the connection
    @PreDestroy
    public void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close(); // Close the client connection
        }
    }
}
