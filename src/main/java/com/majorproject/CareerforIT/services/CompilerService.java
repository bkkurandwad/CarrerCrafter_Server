package com.majorproject.CareerforIT.services;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class CompilerService {


    private static final String BASE_PATH = "/app/src/main/resources";
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
}
