package com.developol.polchatex.rest;
import com.developol.polchatex.persistence.UserDto;
import com.developol.polchatex.persistence.UserDtoRepository;
import com.sun.deploy.net.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping(path="/rest")
public class UserRegistrationController {

    private UserDtoRepository userDtoRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;

    public UserRegistrationController(UserDtoRepository userDtoRepository,
                                      InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.userDtoRepository = userDtoRepository;
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
    }

    @PostMapping(path="/add")
    public ResponseEntity addUser(@RequestBody Map<String, Object> body) {
        UserDto userDto = new UserDto();

        userDto.setUsername((String)body.get("username"));
        userDto.setPassword((String)body.get("password"));
        if (userDto.getPassword() != null && !userDto.getPassword().trim().equals("")
                && userDto.getUsername() !=null && !userDto.getUsername().trim().equals("") ) {

            userDto.setPassword("{bcrypt}" + bCryptPasswordEncoder.encode(userDto.getPassword()));
            userDto.setEmail("dupa");

            userDtoRepository.save(userDto);
            inMemoryUserDetailsManager.createUser( User.builder()
                    .username(userDto.getUsername())
                    .password(userDto.getPassword())
                    .roles("USER")
                    .build());
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



}
