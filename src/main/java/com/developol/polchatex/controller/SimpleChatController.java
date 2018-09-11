package com.developol.polchatex.controller;

import com.developol.polchatex.model.MessageX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class SimpleChatController {
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public SimpleChatController(
            SimpMessagingTemplate simpMessagingTemplate
    ) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("chat-socket/send/message")
    public void send(
            @Payload MessageX msg,
            Principal user) {
        System.out.println("elo");
        /*MessageDto out = new MessageDto();
        out.setSender(msg.getSender());
        out.setReceiver(msg.getReceiver());
        out.setContent(msg.getContent());*/
        simpMessagingTemplate.convertAndSendToUser(
                user.getName(), "/secured/user/queue/specific-user", msg);
    }
}
