package com.developol.polchatex.services;

import com.developol.polchatex.Model.WebSocketPayload;
import com.developol.polchatex.persistence.Chat;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    public boolean checkPayload(WebSocketPayload payload) {
        if (payload.getMessageContent() != null && payload.getChatID() != 0) {
            //0 is the default long value and the database IDs start from 1
            return true;
        }
        return false;
    }

    public String getReceiverUsername(Chat chat, String sender) {

        if (chat.getUser1().getUsername().equals(sender)) {
            return chat.getUser2().getUsername();
        } else if ( chat.getUser2().getUsername().equals(sender)) {
            return chat.getUser1().getUsername();
        }
        //TODO: throw exceptions!
        return null;
    }
}
