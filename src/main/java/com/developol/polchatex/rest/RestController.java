package com.developol.polchatex.rest;

import com.developol.polchatex.Model.ChatDTO;
import com.developol.polchatex.Model.MessageDTO;
import com.developol.polchatex.persistence.Chat;
import com.developol.polchatex.persistence.Message;
import com.developol.polchatex.services.MessageService;
import com.developol.polchatex.services.PersistenceService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping(path="/rest")
public class RestController {

    private PersistenceService persistenceService;
    private ModelMapper modelMapper;
    private MessageService messageService;

    public RestController(PersistenceService persistenceService,
                          ModelMapper modelMapper, MessageService messageService) {
        this.persistenceService = persistenceService;
        this.modelMapper = modelMapper;
        this.messageService = messageService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/gethistory")
    public ResponseEntity<List<MessageDTO>> getChatHistory(@RequestParam long chatID) {

        Iterable<Message> queryResult = this.persistenceService.getChatHistory(chatID);
        List<MessageDTO> requestResult = new ArrayList<MessageDTO>();

        if ( queryResult == null) {
            System.out.println("no such chat!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        queryResult.forEach((message -> {
            requestResult.add(this.modelMapper.map(message, MessageDTO.class));
        }));

        return new ResponseEntity<>(requestResult, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path= "/getchatlist")
    public ResponseEntity<List<ChatDTO>> getchatlist() {
        //The username is determined based on the current session,
        // so the user can access only his/her own chat list
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Iterable<Chat> queryResult = this.persistenceService.getchatlist(user);
        LinkedList<ChatDTO> result = new LinkedList<ChatDTO>();

        if (queryResult == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        //TODO: simplify and implement an array of users
        queryResult.forEach(chat -> {
            result.add(this.modelMapper.map(chat, ChatDTO.class));
           // result.getLast().setUsername(this.messageService.getReceiverUsername(chat, user));
            Message message = this.persistenceService.getLastMessage(chat);

            if (message != null) {
                result.getLast().setLastMessage(this.modelMapper.map(message, MessageDTO.class));
            }
        });
        return new ResponseEntity<List<ChatDTO>>(result, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(path="/addchat")
    public ResponseEntity<ChatDTO> addChat(@RequestParam String username) {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Chat chat = this.persistenceService.persistChat(currentUser, username);
        if ( chat == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        ChatDTO chatDTO = new ChatDTO();
       // chatDTO.setUsername(username);
        chatDTO.setId(chat.getId());
        return new ResponseEntity<>(chatDTO, HttpStatus.OK);
    }
}
