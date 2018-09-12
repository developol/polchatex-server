package com.developol.polchatex.socket;

import com.developol.polchatex.persistence.MessageDto;
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

    @Autowired
    public SimpleChatController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/send-message")
    public void sendMessage(@Payload MessageDto message, Principal user,
                            @Header("simpSessionId") String sessionId) {
        System.out.println("message: " + message.getContent());
        System.out.println("user: " + user.getName());
        System.out.println("session-id: " + sessionId);

        message.setContent("Message sent at moment: " + LocalDateTime.now().toString() + " by " + user.getName());
        simpMessagingTemplate.convertAndSendToUser(
                "mariusz", "/user/queue/specific-user", message);
        // "mariusz" is an username - here you can put your receiver's username
    }

}
