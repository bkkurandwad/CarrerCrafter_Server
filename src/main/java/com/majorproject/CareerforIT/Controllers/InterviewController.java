package com.majorproject.CareerforIT.Controllers;

import com.majorproject.CareerforIT.DTO.CompilerRequest;
import com.majorproject.CareerforIT.DTO.InterviewRequest;
import com.majorproject.CareerforIT.services.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/interview")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @PostMapping("/submitdetails")
    public String submitDetails(@RequestBody InterviewRequest interviewRequest) {

        return interviewService.submitDetails(interviewRequest);
    }

    @GetMapping("/details/{mockId}")
    public InterviewRequest getInterviewDetails(@PathVariable("mockId") String mockId) {
        return interviewService.getInterviewDetails(mockId);
    }

}
