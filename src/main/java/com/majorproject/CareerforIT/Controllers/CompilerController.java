package com.majorproject.CareerforIT.Controllers;

import com.majorproject.CareerforIT.Context.RequestContext;
import com.majorproject.CareerforIT.DTO.CodeShowRequest;
import com.majorproject.CareerforIT.DTO.CodeShowResponse;
import com.majorproject.CareerforIT.DTO.CodeSubmitRequest;
import com.majorproject.CareerforIT.DTO.CompilerRequest;
import com.majorproject.CareerforIT.services.CompilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        String lang = compilerRequest.getLang();
        // Delegate the logic to the service layer
        return compilerService.compileCode(code, lang);
    }

    @PostMapping("/submit")
    public String submit(@RequestHeader("Username") String username, @RequestBody CodeSubmitRequest codeSubmitRequest) {
        return compilerService.submitCode(username, codeSubmitRequest);
    }

    @PostMapping("/show")
    public List<CodeShowResponse> show (@RequestHeader("username") String username, @RequestBody CodeShowRequest show){
        List<CodeShowResponse> res = compilerService.get_codes(username, show);
        return res;
    }
}