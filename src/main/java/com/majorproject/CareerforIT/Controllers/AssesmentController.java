package com.majorproject.CareerforIT.Controllers;

import com.majorproject.CareerforIT.DTO.AssesQuestionRequest;
import com.majorproject.CareerforIT.DTO.AssesmentQuestionResponse;
import com.majorproject.CareerforIT.DTO.AssesmentResponse;
import com.majorproject.CareerforIT.DTO.QuestionResponse;
import com.majorproject.CareerforIT.services.AssesmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/assesment/")
public class AssesmentController {

    @Autowired
    private AssesmentsService AssesService;

    @GetMapping("/list")
    public List<AssesmentResponse> getAssesments (){
        List<AssesmentResponse> res = AssesService.getAssesments();
        return res;
    }

    @GetMapping("/questions")
    public List<AssesmentQuestionResponse> getQuestions(@RequestBody AssesQuestionRequest asqreq) {
       List<AssesmentQuestionResponse> questions = AssesService.getQuestionsForAssessment(asqreq.getAssignID());
        return questions;
    }


}