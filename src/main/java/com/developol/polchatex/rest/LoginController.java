package com.developol.polchatex.rest;

import com.developol.polchatex.persistence.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;

@Controller
public class LoginController {
        @RequestMapping("/login")
        public boolean login(@RequestBody User user) {
            return
                    user.getUsername().equals("user") && user.getPassword().equals("password");
        }

        @RequestMapping("/user")
        public Principal user(HttpServletRequest request) {
            String authToken = request.getHeader("Authorization")
                    .substring("Basic".length()).trim();
            return () ->  new String(Base64.getDecoder()
                    .decode(authToken)).split(":")[0];
        }
}
