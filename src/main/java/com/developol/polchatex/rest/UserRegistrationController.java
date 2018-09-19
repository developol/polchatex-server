package com.developol.polchatex.rest;
import com.developol.polchatex.services.PersistenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(path="/registration")
public class UserRegistrationController {


    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
    private PersistenceService persistenceService;

    public UserRegistrationController(InMemoryUserDetailsManager inMemoryUserDetailsManager,
                                      PersistenceService persistenceService) {
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
        this.persistenceService = persistenceService;
    }

    @CrossOrigin
    @PostMapping(path="/add")
    public ResponseEntity addUser(@RequestBody Map<String, Object> body) {
        String username = (String)body.get("username");
        String password = (String)body.get("password");

        if (password == null || username == null
                || password.trim().equals("")
                || username.trim().equals("") ) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }

        if (this.persistenceService.getUser(username) != null) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        this.persistenceService.persistUser(username, password);

        inMemoryUserDetailsManager.createUser(User.builder()
                .username(username)
                .password(password)
                .roles("USER")
                .build());
            return new ResponseEntity(HttpStatus.OK);
        }
    }

