package com.developol.polchatex.socket;

import com.developol.polchatex.Model.WebSocketPayload;
import com.developol.polchatex.persistence.MessageDto;
import com.developol.polchatex.services.MessageService;
import com.developol.polchatex.services.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class SimpleChatController {
    private SimpMessagingTemplate simpMessagingTemplate;
    private MessageService messageService;
    private PersistenceService persistenceService;

    @Autowired
    public SimpleChatController(SimpMessagingTemplate simpMessagingTemplate,
                                MessageService messageService, PersistenceService persistenceService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageService = messageService;
        this.persistenceService = persistenceService;
    }

    @MessageMapping("/send-message")
    public void sendMessage(@Payload WebSocketPayload payload, Principal user,
                            @Header("simpSessionId") String sessionId) {

        if (payload == null || !this.messageService.checkPayload(payload)) {
            //TODO: notify the sender, that something went wrong
            //not supported yet
        }

        if (this.persistenceService.persistMessage(payload, user.getName())) {
            //TODO: send message over websocket
            return;
        }
        //TODO: notify the sender, that something went wrong
        //not supported yet



















//        System.out.println("message: " + message.getContent());
//        System.out.println("user: " + user.getName());
//        System.out.println("session-id: " + sessionId);
//
//        message.setContent("Message sent at moment: " + LocalDateTime.now().toString() + " by " + user.getName());
//        simpMessagingTemplate.convertAndSendToUser(
//                "kupa", "/user/queue/specific-user", message);
//        // "mariusz" is an username - here you can put your receiver's username
    }

}
