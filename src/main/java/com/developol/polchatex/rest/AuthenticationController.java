package com.developol.polchatex.rest;

import com.developol.polchatex.properties.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(path="/security")
public class AuthenticationController {
    private final String serverDomain;
    public AuthenticationController(@Autowired Properties properties) {
        serverDomain = properties.getServerDomain();
    }


    //@CrossOrigin(value={"https://polchatex-front.herokuapp.com", "https://polchatex-front2.herokuapp.com",
     //       "http://localhost:4200"})
    @CrossOrigin
    @GetMapping(path="/tknauth")
    public @ResponseBody
    ResponseEntity<String> greeting(HttpServletResponse response) {
        Cookie cookie = new Cookie("JSESSIONID", RequestContextHolder.currentRequestAttributes().getSessionId());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return new ResponseEntity<>(RequestContextHolder.currentRequestAttributes().getSessionId(),
                HttpStatus.OK);
    }
    @CrossOrigin
    @GetMapping(path="/logout")
    public ResponseEntity<HttpStatus> logout(HttpServletResponse response, HttpServletRequest request) {
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
