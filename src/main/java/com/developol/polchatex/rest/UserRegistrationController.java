package com.developol.polchatex.rest;
import com.developol.polchatex.persistence.User;
import com.developol.polchatex.persistence.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(path="/registration")
public class UserRegistrationController {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;

    public UserRegistrationController(UserRepository userRepository,
                                      InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.userRepository = userRepository;
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
    }

    @CrossOrigin
    @PostMapping(path="/add")
    public ResponseEntity addUser(@RequestBody Map<String, Object> body) {
        User user = new User();

        user.setUsername((String)body.get("username"));
        user.setPassword((String)body.get("password"));
        if (user.getPassword() != null && !user.getPassword().trim().equals("")
                && user.getUsername() !=null && !user.getUsername().trim().equals("") ) {

            user.setPassword("{bcrypt}" + bCryptPasswordEncoder.encode(user.getPassword()));
            user.setEmail("dupa");

            userRepository.save(user);
            inMemoryUserDetailsManager.createUser( org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles("USER")
                    .build());
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



}
