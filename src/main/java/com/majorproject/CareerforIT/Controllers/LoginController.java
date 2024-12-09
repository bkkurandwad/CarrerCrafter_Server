package com.majorproject.CareerforIT.Controllers;

import com.majorproject.CareerforIT.Context.RequestContext;
import com.majorproject.CareerforIT.DTO.LoginRequest;
import com.majorproject.CareerforIT.DTO.LoginResponse;
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

        String token = RequestContext.getJwtToken();
        if (token != null) {
            // Process the token (e.g., validate the token)
            System.out.println("JWT Token: " + token);
        } else {
            System.out.println("No token found in the request.");
        }

        // Query the database to find the user with the given email
        Document userDoc = collection.find(new Document("email", email)).first();

        if (userDoc == null) {
            return new LoginResponse("User not found", null, null); // User not found
        }

        // Check if the password matches (assuming passwords are hashed)
        String storedPasswordHash = userDoc.getString("password");
        if (!checkPasswordHash(password, storedPasswordHash)) {  // Assuming checkPasswordHash is implemented
            return new LoginResponse("Wrong credentials", null, null); // Wrong password
        }

        // Get the user ID from the database document (you can also use email if needed)
        String userId = userDoc.getObjectId("_id").toString();

        // Generate JWT tokens (Access Token & Refresh Token)
        String accessToken = generateJwtToken(userId, email, 3600000); // Access token valid for 1 hour
        String refreshToken = generateJwtToken(userId, email, 259200000); // Refresh token valid for 3 days

        return new LoginResponse("Login Successful", accessToken, refreshToken); // Return success message, access token, and refresh token
    }

    // Utility method to check password hash (this assumes you are using a hashing library like bcrypt)
    private boolean checkPasswordHash(String rawPassword, String storedPasswordHash) {
        // You should implement password hashing checking logic here
        // Example: return BCrypt.checkpw(rawPassword, storedPasswordHash);
        return rawPassword.equals(storedPasswordHash);  // Replace with actual hashing check
    }

    // Utility method to generate JWT token
    private String generateJwtToken(String userId, String email, long expirationTime) {
        return Jwts.builder()
                .setSubject(email)  // You can include other fields here like userId
                .claim("userId", userId)  // Add user ID to the token payload
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // Set expiration time dynamically
                .signWith(SignatureAlgorithm.HS256, "your-secure-key") // Use a secure key (environment variable or config)
                .compact();
    }
}
