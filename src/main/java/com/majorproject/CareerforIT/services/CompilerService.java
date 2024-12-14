package com.majorproject.CareerforIT.services;

import com.majorproject.CareerforIT.DTO.CodeShowRequest;
import com.majorproject.CareerforIT.DTO.CodeShowResponse;
import com.majorproject.CareerforIT.DTO.CodeSubmitRequest;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.client.MongoDatabase;
import org.bson.types.Code;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompilerService {
    private final MongoCollection<Document> collection;

    private static final String BASE_PATH = "/app/src/main/resources";

    public CompilerService(MongoDatabase mongoDatabase) {
        this.collection = mongoDatabase.getCollection("code_submissions");
    }

    public String compileCode(String code, String lang) {
        // Define the directory and file path
        String directoryPath = "src/main/resources";
        String filename = "";
        switch (lang){
            case "c" : filename = "/main.cpp";
                        break;
            case "py" : filename = "/main.py";
                        break;
            case "java" : filename = "/main.java";
                        break;
            default: return "Lang Payload Error";

        }

        String filePath = directoryPath + filename;


        // Step 1: Ensure the directory exists
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean directoryCreated = directory.mkdirs();  // Create the directory if it doesn't exist
            if (!directoryCreated) {
                return "Error creating directory: " + directoryPath;
            }
        }

        // Step 2: Write code to main2.cpp
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(code);
        } catch (IOException e) {
            return "Error writing code to file: " + e.getMessage();
        }
        System.out.println(code + "\nWritten to file: " + filePath);

        // Step 3: Run Docker container with volume mounting
        try {
            String command = "";
            if (lang.equals("c")) {
                command = "g++ " + BASE_PATH + "/main.cpp -o " + BASE_PATH + "/main && " + BASE_PATH + "/main";
            } else if (lang.equals("py")) {
                command = "python3 " + BASE_PATH + "/main.py";
            } else if (lang.equals("java")) {
                command = "javac " + BASE_PATH + "/main.java && java -cp " + BASE_PATH + " main";
            }

            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);

            Process process = processBuilder.start();

            // Capture the standard output from the Docker container
            String output;
            try (InputStream inputStream = process.getInputStream()) {
                output = new String(inputStream.readAllBytes());
            }

            // Capture the error output from the Docker container
            String error;
            try (InputStream errorStream = process.getErrorStream()) {
                error = new String(errorStream.readAllBytes());
            }

            // Wait for the process to complete and check for errors
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                return "Compilation failed with error:\n" + error;
            }

            System.out.println(output);
            // Return the successful output
            return output;

        } catch (IOException | InterruptedException e) {
            return "Error running Docker container: " + e.getMessage();
        }
    }
    public String submitCode (String username, CodeSubmitRequest req){
        Document doc = new Document("username", username)
                .append("ques_id", req.getQuesid()) //
                .append("lang", req.getLang())
                .append("code", req.getCode())
                .append("result", req.getResult());

        try {
            // Insert the document into the MongoDB collection
            collection.insertOne(doc);
            // Log the inserted document for confirmation
            System.out.println("Inserted document: {}"+ doc.toJson());
            return "Code Submitted successfully";
        } catch (Exception e) {
            System.out.println("Error inserting document: "+ e);
            return "Failed to submit the code";
        }
    }
    public List<CodeShowResponse> get_codes (String username, CodeShowRequest show){
        int quesid = show.getQuesid();
        List<CodeShowResponse> responses = new ArrayList<>();

        try {
            // Create a query filter to find all matching documents
            Document query = new Document("username", username)
                    .append("ques_id", quesid);

            // Fetch all matching documents
            for (Document resultDoc : collection.find(query)) {
                // Map each document to a CodeShowResponse object
                CodeShowResponse showres = new CodeShowResponse();
                showres.setQuesid(resultDoc.getInteger("ques_id"));
                showres.setLang(resultDoc.getString("lang"));
                showres.setCode(resultDoc.getString("code"));
                showres.setResult(resultDoc.getString("result"));

                // Add the response object to the list
                responses.add(showres);
            }

            return responses;

        } catch (Exception e) {
            System.out.println("Error fetching code submissions: " + e.getMessage());
            return null; // or return an empty list if preferred
        }
    }

}
