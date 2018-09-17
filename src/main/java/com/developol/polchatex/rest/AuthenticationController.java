package com.developol.polchatex.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(path="/security")
public class AuthenticationController {

    @CrossOrigin(value="https://polchatex-front.herokuapp.com/")
    @GetMapping(path="/tknauth")
    public @ResponseBody
    ResponseEntity<String> greeting(HttpServletResponse response) {
        Cookie c = new Cookie("JSESSIONID", RequestContextHolder.currentRequestAttributes().getSessionId());
        c.setDomain("agile-hollows-19556.herokuapp.com");
        c.setSecure(true);
        c.setHttpOnly(true);
        response.addCookie(c);
        return new ResponseEntity<String>(RequestContextHolder.currentRequestAttributes().getSessionId(),
                        HttpStatus.OK);
    }
}
