package com.developol.polchatex.socket;

import com.developol.polchatex.model.WebSocketPayload;
import com.developol.polchatex.persistence.Chat;
import com.developol.polchatex.persistence.Message;
import com.developol.polchatex.services.MessageService;
import com.developol.polchatex.services.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

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
        System.out.println(payload.getChatID());
        System.out.println(payload.getMessageContent());

        if (!this.messageService.checkPayload(payload)) {
            //notify the sender, that something went wrong
            //not supported yet
            System.out.println("check FALSE");
            return;
        }
        Chat chat = this.persistenceService.getChat(payload.getChatID());
        if (chat == null) {
            System.out.println("no such chat");
            return;
        }
        Message result = this.persistenceService.persistMessage(payload, user.getName(), chat);

        if (result == null) {
            //notify the sender, that something went wrong
            //not supported yet
            System.out.println("message NOT persisted");
            return;
        }

        String receiver = this.messageService.getReceiverUsername(chat, user.getName());
        if ( receiver == null) {
            //notify the sender, that something went wrong
            //not supported yet
            System.out.println("No such user in that chatID!!");
            return;
        }
        simpMessagingTemplate.convertAndSendToUser(
                receiver, "/user/queue/specific-user", result);
    }

}
