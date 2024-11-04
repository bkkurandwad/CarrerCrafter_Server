package com.majorproject.CareerforIT.Controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.majorproject.CareerforIT.DTO.CompilerRequest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/code")
public class CompilerController {

    @PostMapping("/compile")
    public String compile(@RequestBody CompilerRequest compilerRequest) {
        String code = compilerRequest.getCode();

        // Step 1: Write code to main.cpp
        String filePath = "E:/Projects after Maersk/docker practice/First_project/server api src/main2.cpp";
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(code);
        } catch (IOException e) {
            return "Error writing code to file: " + e.getMessage();
        }
        System.out.println(code + "\nWritten to file: " + filePath);



        // Step 2: Run Docker container with volume mounting
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "docker", "run", "--rm",
                    "-v", "E:/Projects after Maersk/docker practice/First_project/server api src/main2.cpp",
                    "my-cpp-app:latest" // Your Docker image name
            );

            // Setting the working directory to the mounted path inside the container
            processBuilder.directory(new File("E:/Projects after Maersk/docker practice/First_project/server api src"));
            Process process = processBuilder.start();

            // Capture Docker output
            String output2;
            try (InputStream inputStream = process.getInputStream()) {
                output2 = new String(inputStream.readAllBytes());
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                // Capture any errors if Docker fails
                try (InputStream errorStream = process.getErrorStream()) {
                    String error = new String(errorStream.readAllBytes());
                    return "Compilation failed with error:\n" + error;
                }
            }

            System.out.println(output2);
            // Return successful output
            return output2;

        } catch (IOException | InterruptedException e) {
            return "Error running Docker container: " + e.getMessage();
        }
    }
}



//package com.majorproject.CareerforIT.Controllers;
//
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import com.majorproject.CareerforIT.DTO.CompilerRequest;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/code")
//public class CompilerController {
//
//    @PostMapping("/compile")
//    public String compile(@RequestBody CompilerRequest compilerRequest) {
//        String code = compilerRequest.getCode();
//
//        // Step 1: Write code to main.cpp
//        String filePath = "E:/Projects after Maersk/docker practice/First_project/server api src/main.cpp";
//        try (FileWriter writer = new FileWriter(filePath)) {
//            writer.write(code);
//        } catch (IOException e) {
//            return "Error writing code to file: " + e.getMessage();
//        }
//
//        // Step 2: Run Docker container with volume mounting
//        try {
//            ProcessBuilder processBuilder = new ProcessBuilder(
//                    "docker", "run", "--rm",
//                    "-v", "E:/Projects after Maersk/docker practice/First_project/server api src/main.cpp",
//                    "my-cpp-app:latest"
//            );
//            Process process = processBuilder.start();
//
//            // Capture Docker output
//            String output;
//            try (InputStream inputStream = process.getInputStream()) {
//                output = new String(inputStream.readAllBytes());
//            }
//
//            int exitCode = process.waitFor();
//            if (exitCode != 0) {
//                // Capture any errors if Docker fails
//                try (InputStream errorStream = process.getErrorStream()) {
//                    String error = new String(errorStream.readAllBytes());
//                    return "Compilation failed with error:\n" + error;
//                }
//            }
//
//            // Return successful output
//            return output;
//
//        } catch (IOException | InterruptedException e) {
//            return "Error running Docker container: " + e.getMessage();
//        }
//    }
//
//    //   return null;
//    }
//
