package com.majorproject.CareerforIT.controllers;

import com.majorproject.CareerforIT.DTO.ResumeRequest;
import com.majorproject.CareerforIT.services.ResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateResume(@RequestBody ResumeRequest request) {
        try {
            String htmlResume = resumeService.generateResumeHtml(request);
            return ResponseEntity.ok(htmlResume);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error generating resume: " + e.getMessage());
        }
    }
}
