package com.developol.polchatex.rest;
import com.developol.polchatex.persistence.UserDto;
import com.developol.polchatex.persistence.UserDtoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Controller
@RequestMapping(path="/rest")
public class MainController {

    private UserDtoRepository userDtoRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public MainController(UserDtoRepository userDtoRepository) {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.userDtoRepository = userDtoRepository;
    }

    @GetMapping(path="/hello")
    public @ResponseBody String greeting() {
        return "Hello World!";
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
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



}
