package com.developol.polchatex.rest;

import com.developol.polchatex.properties.Properties;
import org.springframework.beans.factory.annotation.Autowired;
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
    private Properties properties;
    public AuthenticationController(@Autowired Properties properties) {
        this.properties = properties;
    }

    @GetMapping(path="/tknauth")
    public @ResponseBody
    ResponseEntity<String> authorize(HttpServletResponse response) {
        Cookie c = new Cookie("JSESSIONID", RequestContextHolder.currentRequestAttributes().getSessionId());
        c.setDomain("127.0.0.1");
        c.setSecure(true);
        c.setHttpOnly(true);
        response.addCookie(c);
        return new ResponseEntity<>(RequestContextHolder.currentRequestAttributes().getSessionId(),
                HttpStatus.OK);
    }
}
