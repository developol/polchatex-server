package com.developol.polchatex.services;

import com.developol.polchatex.Model.WebSocketPayload;
import com.developol.polchatex.persistence.MessageDto;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    public boolean checkPayload(WebSocketPayload payload) {
        if (payload.getMessageContent() != null && payload.getChatID() != 0) {
            //0 is the default long value and the database ID's start from 1
            return true;
        }
        return false;
    }
}
