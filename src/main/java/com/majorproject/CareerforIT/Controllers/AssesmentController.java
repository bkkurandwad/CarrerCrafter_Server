package com.majorproject.CareerforIT.Controllers;

import com.majorproject.CareerforIT.DTO.AssesmentResponse;
import com.majorproject.CareerforIT.services.AssesmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/assesment/")
public class AssesmentController {

    private final AssesmentsService AssesService;

    @Autowired
    public AssesmentController(AssesmentsService AssesService) {
        this.AssesService = AssesService;
    }

    @GetMapping("/list")
    public List<AssesmentResponse> getAssesments (){
        List<AssesmentResponse> res = AssesService.getAssesments();
        return res;
    }

}