package com.majorproject.CareerforIT.Controllers;

import com.majorproject.CareerforIT.DTO.LoginRequest;
import com.majorproject.CareerforIT.DTO.LoginResponse; // Import the DTO
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/login")
public class LoginController {

    private final MongoCollection<Document> collection;

    @Autowired
    public LoginController(MongoDatabase mongoDatabase) {
        this.collection = mongoDatabase.getCollection("StudentDetails"); // Adjust the collection name if needed
    }

    @PostMapping("/student")
    public LoginResponse auth(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getUsername(); // Username is the email
        String password = loginRequest.getPassword();
        System.out.println("Received login request - Email: " + email + ", Password: " + password);

        // Query the database to find the user with the given email
        Document userDoc = collection.find(new Document("email", email)).first();

        if (userDoc == null) {
            return new LoginResponse("User not found", null, null); // User not found
        }

        // Check if the password matches
        String storedPassword = userDoc.getString("password");
        if (!storedPassword.equals(password)) {
            return new LoginResponse("Wrong credentials", null, null); // Wrong password
        }

        // User found and password matches, generate JWT and refresh token
        String accessToken = Jwts.builder()
                .setSubject(email) // Use email as the subject of the token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // Access token valid for 1 hour
                .signWith(SignatureAlgorithm.HS256, "ABCD") // Use a secure key in production
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(email) // Use email as the subject of the token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 259200000)) // Refresh token valid for 3 days
                .signWith(SignatureAlgorithm.HS256, "ABCD") // Use a secure key in production
                .compact();

        return new LoginResponse("Login Successful", accessToken, refreshToken); // Return success message, access token, and refresh token
    }
}
