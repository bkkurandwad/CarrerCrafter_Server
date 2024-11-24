package com.majorproject.CareerforIT.services;

import com.majorproject.CareerforIT.DTO.ResumeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ResumeService {

    private static final Logger logger = LoggerFactory.getLogger(ResumeService.class);

    public String generateResumeHtml(ResumeRequest request) throws Exception {
        try {
            logger.info("Starting HTML resume generation process for: {}", request.getName());

            // Read the HTML template
            Path htmlTemplatePath = Path.of("src/main/resources/templates/resume.html");
            String htmlTemplate = Files.readString(htmlTemplatePath);
            logger.debug("HTML template read successfully");

            // Replace placeholders with actual data
            String filledHtml = htmlTemplate
                    .replace("{{NAME}}", request.getName())
                    .replace("{{EMAIL}}", request.getEmail())
                    .replace("{{PHONE}}", request.getPhone())
                    .replace("{{EDUCATION}}", request.getEducation())
                    .replace("{{EXPERIENCE}}", request.getExperience())
                    .replace("{{SKILLS}}", request.getSkills())
                    .replace("{{PROJECTS}}", request.getProjects())
                    .replace("{{ACHIEVEMENTS}}", request.getAchievements());
            logger.debug("HTML template filled successfully");

            return filledHtml;
        } catch (Exception e) {
            logger.error("Error generating HTML resume for {}: {}", request.getName(), e.getMessage(), e);
            throw new Exception("Failed to generate HTML resume", e);
        }
    }
}
