package com.majorproject.CareerforIT.services;

import org.springframework.stereotype.Service;

import java.io.*;

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

        System.out.println(System.getProperty("user.dir"));

        try {
            // Create a ProcessBuilder for the "pwd" command
            ProcessBuilder p1 = new ProcessBuilder("pwd");
            Process process1 = p1.start();

            // Capture the output of "pwd"
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(process1.getInputStream()));
            String line1;
            while ((line1 = reader1.readLine()) != null) {
                System.out.println(line1);  // Print output of pwd
            }

            // Wait for the "pwd" process to finish
            process1.waitFor();

            // Create a ProcessBuilder for the "ls" command
            ProcessBuilder p2 = new ProcessBuilder("ls");
            Process process2 = p2.start();

            // Capture the output of "ls"
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(process2.getInputStream()));
            String line2;
            while ((line2 = reader2.readLine()) != null) {
                System.out.println(line2);  // Print output of ls
            }

            // Wait for the "ls" process to finish
            process2.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }



        // Step 3: Run Docker container with volume mounting
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "bash", "-c",
                    "g++ /app/src/main/resources/main2.cpp -o /app/src/main/resources/main2 && /app/src/main/resources/main2"// Compile and run inside container
            );

            processBuilder.directory(new File("/app"));
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
