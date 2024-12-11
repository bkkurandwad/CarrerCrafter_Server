package com.majorproject.CareerforIT.services;

import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

@Service
public class CompilerService {

    public String compileCode(String code) {
        // Define the directory and file path
        String directoryPath = "src/main/resources";
        String filePath = directoryPath + "/main2.cpp";

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
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "bash", "-c",
                    "g++ /src/main/resources/main2.cpp -o /src/main/resources/main2 && /src/main/resources/main2"// Compile and run inside container
            );

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
}
