package com.developol.polchatex.rest;

import com.developol.polchatex.model.MessageDTO;
import com.developol.polchatex.model.ChatDTO;
import com.developol.polchatex.persistence.Chat;
import com.developol.polchatex.persistence.Message;
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

    public RestController(PersistenceService persistenceService,
                          ModelMapper modelMapper) {
        this.persistenceService = persistenceService;
        this.modelMapper = modelMapper;
    }

    @CrossOrigin
    @GetMapping(path = "/gethistory")
    public ResponseEntity<List<MessageDTO>> getChatHistory(@RequestParam long chatID) {
        if (!this.persistenceService.isUserInChat(chatID,SecurityContextHolder.getContext().getAuthentication().getName())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Iterable<Message> queryResult = this.persistenceService.getChatHistory(chatID);
        List<MessageDTO> requestResult = new ArrayList<>();

        if ( queryResult == null) {
            System.out.println("no such chat!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        queryResult.forEach((message -> requestResult.add(this.modelMapper.map(message, MessageDTO.class))));

        return new ResponseEntity<>(requestResult, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(path= "/getchatlist")
    public ResponseEntity<List<ChatDTO>> getchatlist() {
        //The username is determined based on the current session,
        // so the user can access only his/her own chat list

        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Iterable<Chat> queryResult = this.persistenceService.getChatList(user);
        LinkedList<ChatDTO> result = new LinkedList<>();

        if (queryResult == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        //TODO: optimise retrieving last message and usernames list
        //TODO: configure and use modelmapper for chatDTO
        queryResult.forEach(chat -> {
            ChatDTO chatDTO = new ChatDTO();
            chatDTO.setId(chat.getId());
            chatDTO.setChatName(chat.getChatName());
            Message message = this.persistenceService.getLastMessage(chat);
            if (message != null) {
                chatDTO.setLastMessage(this.modelMapper.map(message, MessageDTO.class));
                chatDTO.getLastMessage().setSender(message.getSender().getUsername());
            }
            String[] usernames = this.persistenceService.getChatUsers(chat).toArray(new String[0]);
            chatDTO.setUsernames(usernames);
            result.add(chatDTO);
        });
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(path="/addchat")
    public ResponseEntity<ChatDTO> addChat(@RequestBody String[] usernames) {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        for ( int i =0; i<usernames.length; i++) {
            if (this.persistenceService.getUser(usernames[i]) == null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        if (usernames.length == 1 && this.persistenceService.privateChatExists(currentUser, usernames[0])) {
           return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Chat chat = this.persistenceService.persistChat(currentUser, usernames);
        if ( chat == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setUsernames(usernames);
        chatDTO.setId(chat.getId());
        return new ResponseEntity<>(chatDTO, HttpStatus.OK);
    }
}
