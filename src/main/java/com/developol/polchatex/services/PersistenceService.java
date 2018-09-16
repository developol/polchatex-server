package com.developol.polchatex.services;

import com.developol.polchatex.Model.ChatDTO;
import com.developol.polchatex.Model.WebSocketPayload;
import com.developol.polchatex.persistence.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersistenceService {
    private MessageRepository messageRepository;
    private ChatRepository chatRepository;
    private UserRepository userRepository;

    public PersistenceService(MessageRepository messageRepository,
                              ChatRepository chatRepository,
                              UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }


    public Message persistMessage(WebSocketPayload payload, String senderUsername, Chat chat) {
        Message message = new Message();
        message.setChat(chat);

        User sender = this.userRepository.getByUsername(senderUsername);
        if (sender == null) {
            return null;
        }

        message.setSender(sender);
        message.setContent(payload.getMessageContent());
        message.setRead(false); // read/unread mechanism isn't supported yet
        this.messageRepository.save(message);
        return message;
    }

    public Chat persistChat(String username1, String username2) {
        User user1 = this.userRepository.getByUsername(username1);
        User user2 = this.userRepository.getByUsername(username2);
        if (user2 == null || user1 == null || this.chatExists(user1, user2)) {
            return null;
        }
        Chat chat = new Chat();
        chat.setUser1(user1);
        chat.setUser2(user2);
        this.chatRepository.save(chat);
        return chat;
    }

    public User getUser(String username) {
        return this.userRepository.getByUsername(username);
    }

    public Chat getChat(long chatID) {
        return this.chatRepository.getById(chatID);
    }

    public Iterable<Message> getChatHistory(long chatID) {
        return this.messageRepository.findAllByChat_Id(chatID);
    }

    public Iterable<Chat> getchatlist(String username) { return this.chatRepository.findAllByUserName(username); }

    public Message getLastMessage(Chat chat) {
        return this.messageRepository.getLastMessageByChat(chat);
    }

    private boolean chatExists(User user1, User user2) {
        return (this.chatRepository.countAllByUser1EqualsAndUser2Equals(user1, user2) != 0 ||
                this.chatRepository.countAllByUser1EqualsAndUser2Equals(user2, user1) != 0);
    }
}
