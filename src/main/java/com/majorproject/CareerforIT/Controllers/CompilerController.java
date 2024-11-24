package com.majorproject.CareerforIT.Controllers;

import com.majorproject.CareerforIT.DTO.CompilerRequest;
import com.majorproject.CareerforIT.services.CompilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/code")
public class CompilerController {

    private final CompilerService compilerService;

    // Injecting the CompilerService
    @Autowired
    public CompilerController(CompilerService compilerService) {
        this.compilerService = compilerService;
    }

    @PostMapping("/compile")
    public String compile(@RequestBody CompilerRequest compilerRequest) {
        String code = compilerRequest.getCode();
        // Delegate the logic to the service layer
        return compilerService.compileCode(code);
    }
}