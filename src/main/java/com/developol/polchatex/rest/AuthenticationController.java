package com.developol.polchatex.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

@Controller
@RequestMapping(path="/security")
public class AuthenticationController {

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path="/tknauth")
    public @ResponseBody
    ResponseEntity<String> greeting() {
                return new
                        ResponseEntity<String>(RequestContextHolder.currentRequestAttributes().getSessionId(),
                        HttpStatus.OK);
    }
}
