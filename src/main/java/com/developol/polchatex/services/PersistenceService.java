package com.developol.polchatex.services;

import com.developol.polchatex.Model.WebSocketPayload;
import com.developol.polchatex.persistence.*;
import org.springframework.stereotype.Service;

@Service
public class PersistenceService {
    private MessageDtoRepository messageDtoRepository;
    private ChatDtoRepository chatDtoRepository;
    private UserDtoRepository userDtoRepository;

    public PersistenceService(MessageDtoRepository messageDtoRepository,
                              ChatDtoRepository chatDtoRepository,
                              UserDtoRepository userDtoRepository) {
        this.messageDtoRepository = messageDtoRepository;
        this.chatDtoRepository = chatDtoRepository;
        this.userDtoRepository = userDtoRepository;
    }


    public boolean persistMessage(WebSocketPayload payload, String senderUsername) {
        MessageDto message = new MessageDto();
        ChatDto chat = this.chatDtoRepository.getById(payload.getChatID());
        if (chat == null) {
            return false;
        }
        message.setChat(chat);

        UserDto sender = this.userDtoRepository.getByUsername(senderUsername);
        if (sender == null) {
            return false;
        }
        message.setSender(sender);
        //TODO: check and populate other fields

        message.setRead(false); // read/unread mechanism isn't supported yet
        return true;
    }

    public UserDto getUser(String username) {
        return this.userDtoRepository.getByUsername(username);
    }
}
